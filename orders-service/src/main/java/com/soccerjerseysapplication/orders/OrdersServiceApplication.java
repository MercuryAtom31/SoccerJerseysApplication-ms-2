package com.soccerjerseysapplication.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}

//	@Bean
//	RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		restTemplate.setRequestFactory(requestFactory);
//		return restTemplate;
//	}

}
