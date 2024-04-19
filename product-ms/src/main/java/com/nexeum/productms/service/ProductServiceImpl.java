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
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.UUID;

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
    public Mono<ResponseEntity<ServiceResponse>> addProduct(Mono<FilePart> imageFile, String name, String description, String brandName, BigDecimal pricePerUnit, BigDecimal productWholeSalePrice, Long noOfStocks) {
        return Mono.fromCallable(() -> uploadImage((MultipartFile) imageFile))
                .map(imageUrl -> {
                    Product product = new Product();
                    product.setName(name);
                    product.setDescription(description);
                    product.setBrandName(brandName);
                    product.setPricePerUnit(pricePerUnit);
                    product.setProductWholeSalePrice(productWholeSalePrice);
                    product.setNoOfStocks(noOfStocks);
                    product.setProductImageUrl(String.valueOf(imageUrl));
                    return product;
                })
                .flatMap(productRepository::save)
                .map(product -> {
                    ServiceResponse response = new ServiceResponse("200", "Product added successfully");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .onErrorReturn(new ResponseEntity<>(new ServiceResponse("501", "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found")))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error")));
    }

    public Mono<String> uploadImage(MultipartFile imageFile) {
        return Mono.fromCallable(() -> convertMultiPartToFile(imageFile))
                .map(file -> {
                    String filename = generateFileName(imageFile);
                    String imageUrl = bucketName + endpointUrl + "/" + filename;
                    uploadFileTos3bucket(filename, file);
                    deleteTemporaryFile(file);
                    return imageUrl;
                })
                .onErrorResume(e -> {
                    logger.error("Error occurred while uploading image to S3 bucket", e);
                    return Mono.empty();
                });
    }

    private void deleteTemporaryFile(File file) {
        if (file.exists()) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                logger.error("Failed to delete file: {}", file.getAbsolutePath(), e);
            }
        } else {
            logger.warn("File does not exist: {}", file.getAbsolutePath());
        }
    }

    private File convertMultiPartToFile(MultipartFile file) {
        try {
            File convertedFile = Files.createTempFile(null, null).toFile();
            Files.write(convertedFile.toPath(), file.getBytes());
            return convertedFile;
        } catch (IOException e) {
            logger.error("Failed to convert multipart file to file: {}", file.getOriginalFilename(), e);
            return null;
        }
    }


    private String generateFileName(MultipartFile multiPart) {
        String originalFilename = multiPart.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file does not have a name");
        }
        String uuid = UUID.randomUUID().toString();
        String sanitizedFilename = originalFilename.replace(" ", "_");
        return String.format("%s-%s", uuid, sanitizedFilename);
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        if (!file.exists()) {
            logger.warn("File does not exist: {}", file.getAbsolutePath());
            return;
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        try {
            s3client.putObject(putObjectRequest, file.toPath());
        } catch (Exception e) {
            logger.error("Failed to upload file to S3 bucket: {}", file.getAbsolutePath(), e);
        }
    }
}
