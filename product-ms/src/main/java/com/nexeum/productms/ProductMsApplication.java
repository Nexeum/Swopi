package com.nexeum.productms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.nexeum.productms.repository"})
public class ProductMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMsApplication.class, args);
    }

}
