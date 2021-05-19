package com.thien.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Starting point of the spring application
 * @author TN
 *
 */

@SpringBootApplication
public class StarWarsApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(StarWarsApplication.class, args);		
	}	
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}	


}
