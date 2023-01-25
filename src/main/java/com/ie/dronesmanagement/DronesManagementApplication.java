package com.ie.dronesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesManagementApplication.class, args);
	}

}
