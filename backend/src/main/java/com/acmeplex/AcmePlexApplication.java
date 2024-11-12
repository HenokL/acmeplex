package com.acmeplex;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})


public class AcmePlexApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmePlexApplication.class, args);
    }
}
