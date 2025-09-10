package com.nauval.peminjamanservice.peminjamanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class PeminjamanserviceApplication {
	@Bean
	public static void main(String[] args) {
		SpringApplication.run(PeminjamanserviceApplication.class, args);
	}

}
