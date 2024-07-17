package com.gcu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.gcu")

@SpringBootApplication
public class CloudTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudTestApplication.class, args);
	}

}
