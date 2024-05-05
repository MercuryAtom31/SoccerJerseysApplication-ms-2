package com.soccerjerseysapplication.apigateway.domainclientlayer.jerseys;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;
import com.soccerjerseysapplication.apigateway.utils.HttpErrorInfo;
import com.soccerjerseysapplication.apigateway.utils.exceptions.InvalidInputException;
import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class JerseyServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String JERSEY_SERVICE_BASE_URL;

    public JerseyServiceClient(RestTemplate restTemplate,
                               ObjectMapper mapper,
                               @Value("${app.jerseys-service.host}") String jerseyServiceHost,
                               @Value("${app.jerseys-service.port}") String jerseyServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;

        JERSEY_SERVICE_BASE_URL  = "http://" + jerseyServiceHost + ":" + jerseyServicePort + "/api/v1/jerseys";
    }

    public List<JerseyResponseModel> getAllJerseys() {
        try {
            String url = JERSEY_SERVICE_BASE_URL;

            JerseyResponseModel [] jerseyResponseModel = restTemplate.getForObject(url, JerseyResponseModel[].class);

            return Arrays.asList(jerseyResponseModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public JerseyResponseModel getJerseyByJerseyId(String jerseyId) {
        try {
            String url = JERSEY_SERVICE_BASE_URL + "/" + jerseyId;

            JerseyResponseModel jerseyResponseModel = restTemplate.getForObject(url, JerseyResponseModel.class);

            return jerseyResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public JerseyResponseModel createJersey(JerseyRequestModel jerseyRequestModel) {
        try {
            String url = JERSEY_SERVICE_BASE_URL;

            JerseyResponseModel jerseyResponseModel = restTemplate.postForObject(url, jerseyRequestModel, JerseyResponseModel.class);

            return jerseyResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public JerseyResponseModel updateJersey(String jerseyId, JerseyRequestModel jerseyRequestModel) {
        try {
            String url = JERSEY_SERVICE_BASE_URL + "/" + jerseyId;

            restTemplate.put(url, jerseyRequestModel);

            return getJerseyByJerseyId(jerseyId);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteJersey(String jerseyId) {
        try {
            String url = JERSEY_SERVICE_BASE_URL + "/" + jerseyId;

            restTemplate.delete(url);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public RuntimeException handleHttpClientException(HttpClientErrorException ex) {

        //include all possible responses from the client
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    public String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

}