//package com.soccerjerseysapplication.orders.domainclientlayer;
//
//import com.soccerjerseysapplication.customers.utils.HttpErrorInfo;
//import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
//import com.soccerjerseysapplication.orders.utils.HttpErrorInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.service.spi.ServiceException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//@Slf4j
//public abstract class BaseServiceClient {
//    protected final RestTemplate restTemplate;
//    protected final ObjectMapper objectMapper;
//
//    public BaseServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
//        this.restTemplate = restTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    protected <T> T getForObject(String url, Class<T> responseType) {
//        try {
//            return restTemplate.getForObject(url, responseType);
//        } catch (HttpClientErrorException ex) {
//            throw handleHttpClientException(ex);
//        }
//    }
//
//    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
//        if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
//            return new NotFoundException(getErrorMessage(ex));
//        }
//        return new ServiceException("Service exception occurred: " + getErrorMessage(ex));
//    }
//
//    private String getErrorMessage(HttpClientErrorException ex) {
//        try {
//            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
//        } catch (IOException ioex) {
//            log.error("Error parsing error message", ioex);
//            return "Unknown error occurred";
//        }
//    }
//}
//
