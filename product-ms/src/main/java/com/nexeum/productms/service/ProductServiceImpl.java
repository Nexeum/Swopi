package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import com.nexeum.productms.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private S3Client s3client;

    @Value("${aws.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("$createdawsendpointUrl}")
    private String createdawsendpointUrl;

    @PostConstruct
    private void initializeAmazon() {
        AwsCredentials credentials = AwsBasicCredentials.create(this.accessKey, this.secretKey);
        this.s3client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> addProduct(MultipartFile imageFile, String name, String description, String brandName, BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks) {
        ServiceResponse response = new ServiceResponse();
        try {
            Product product = new Product();

            product.setName(name);
            product.setDescription(description);
            product.setBrandName(brandName);
            product.setPricePerUnit(pricePerUnit);
            product.setProductWholeSalePrice(productWholeSalePrice);
            product.setNoOfStocks(noOfStocks);
            String imageUrl = uploadImage(imageFile);
            product.setProductImageUrl(imageUrl);
            productRepository.save(product);
            response.setCode("200");
            response.setResponse("Product added successfully");
            return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setCode("501");
            response.setResponse("Internal server error");
            return new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllProducts() {
        ServiceResponse response = new ServiceResponse();
        try {
            List<Product> allProducts = productRepository.findAll();
            if (!(allProducts.isEmpty())) {
                return new ResponseEntity<List<Product>>(allProducts, HttpStatus.OK);
            } else {
                response.setCode("404");
                response.setResponse("No products found");
                return new ResponseEntity<ServiceResponse>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setCode("501");
            response.setResponse("Internal server error");
            return new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String uploadImage(MultipartFile imageFile) {
        String imageUrl = "";
        try {
            File file = convertMultiPartToFile(imageFile);
            String filename = generateFileName(imageFile);
            imageUrl = bucketName + createdawsendpointUrl + "/" + filename;
            uploadFileTos3bucket(filename, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3client.putObject(putObjectRequest, file.toPath());
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3client.deleteObject(deleteObjectRequest);
        return "Successfully deleted";
    }
}
