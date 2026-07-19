package com.ewha.unis;

import com.ewha.unis.global.config.FileStorageProperties;
import com.ewha.unis.global.config.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties({JwtProperties.class, FileStorageProperties.class})
@EnableJpaAuditing
@SpringBootApplication
public class UnisApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnisApplication.class, args);
	}

}
