package com.nauval.notificationservice.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
// GANTI nama kelas agar lebih konsisten (Service -> service)
public class NotificationserviceApplication {

	public static void main(String[] args) {
		// Sesuaikan nama kelas di sini juga
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}