package com.soccerjerseysapplication.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CustomersServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CustomersServiceApplication.class, args);
	}

//	@Bean
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}
	//@Bean
	//public RestTemplate restTemplate(RestTemplateBuilder builder) {
	//	return builder.build();
	//}

}
