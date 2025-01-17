package com.soccerjerseysapplication.apigateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApiGatewayApplication {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		return new ObjectMapper();
//	}
//	@Bean
//	RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		restTemplate.setRequestFactory(requestFactory);
//		return restTemplate;
//	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
