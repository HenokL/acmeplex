package com.acmeplex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.acmeplex.model")
public class AcmeplexApplication {
	public static void main(String[] args) {
		SpringApplication.run(AcmeplexApplication.class, args);
	}

}
