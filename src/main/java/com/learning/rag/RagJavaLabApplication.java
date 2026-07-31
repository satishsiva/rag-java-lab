package com.learning.rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RagJavaLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagJavaLabApplication.class, args);
	}

}
