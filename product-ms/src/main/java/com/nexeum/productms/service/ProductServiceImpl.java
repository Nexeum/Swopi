package com.nexeum.productms.service;

import com.nexeum.productms.dto.response.ServiceResponse;
import com.nexeum.productms.entity.Product;
import com.nexeum.productms.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private S3Client s3client;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${aws.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AwsCredentials credentials = AwsBasicCredentials.create(this.accessKey, this.secretKey);
        this.s3client = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Object> addProduct(MultipartFile imageFile, String name, String description, String brandName, BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks) {
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
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setCode("501");
            response.setResponse("Internal server error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAllProducts() {
        ServiceResponse response = new ServiceResponse();
        try {
            List<Product> allProducts = productRepository.findAll();
            if (!(allProducts.isEmpty())) {
                return new ResponseEntity<>(allProducts, HttpStatus.OK);
            } else {
                response.setCode("404");
                response.setResponse("No products found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setCode("501");
            response.setResponse("Internal server error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String uploadImage(MultipartFile imageFile) {
        String imageUrl = "";
        try {
            File file = convertMultiPartToFile(imageFile);
            String filename = generateFileName(imageFile);
            imageUrl = bucketName + endpointUrl + "/" + filename;
            uploadFileTos3bucket(filename, file);
            deleteTemporaryFile(file);
        } catch (Exception e) {
            logger.error("Error occurred while uploading image to S3 bucket", e);
        }
        return imageUrl;
    }

    private void deleteTemporaryFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            logger.error("Failed to delete file: " + file.getName(), e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = Files.createTempFile(null, null).toFile();
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        String originalFilename = multiPart.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Uploaded file does not have a name");
        }
        return new Date().getTime() + "-" + originalFilename.replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3client.putObject(putObjectRequest, file.toPath());
    }
}
