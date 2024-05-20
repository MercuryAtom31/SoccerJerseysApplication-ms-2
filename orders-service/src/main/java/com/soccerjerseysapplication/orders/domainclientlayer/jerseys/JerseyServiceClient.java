package com.soccerjerseysapplication.orders.domainclientlayer.jerseys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.orders.utils.HttpErrorInfo;
import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
import com.soccerjerseysapplication.orders.utils.exceptions.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class JerseyServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String  JERSEY_SERVICE_BASE_URL;

    public JerseyServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.jerseys-service.host}") String jerseysServiceHost,
                               @Value("${app.jerseys-service.port}") String jerseysServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.JERSEY_SERVICE_BASE_URL = "http://" + jerseysServiceHost + ":" + jerseysServicePort + "/api/v1/jerseys";
    }

    public JerseyResponseModel getJerseyById(String jerseyId){
        try{
            String url = JERSEY_SERVICE_BASE_URL + "/"+ jerseyId;
            return restTemplate.getForObject(url, JerseyResponseModel.class);
        } catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    public void updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel) {
        try {
            String url = JERSEY_SERVICE_BASE_URL + "/"+ jerseyId;
            restTemplate.put(url, jerseyRequestModel);
        } catch(HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }


    public String getErrorMessage(HttpClientErrorException ex) {
        try{
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex){
            return ioex.getMessage();
        }
    }

    public RuntimeException handleHttpClientException(HttpClientErrorException ex) {

        //include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) { // Thrown when there are DBs issues.
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }
//    private RuntimeException handleHttpClientException(HttpClientErrorException ex){
//        //adding all the exceptions that the service throws
//        if (ex.getStatusCode() == NOT_FOUND)
//            return new NotFoundException(getErrorMessage(ex));
//        return ex;
//    }

}