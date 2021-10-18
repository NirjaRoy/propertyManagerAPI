package com.api.propertyManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PropertyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagerApplication.class, args);
		System.out.println("Welcome");
	}
}
