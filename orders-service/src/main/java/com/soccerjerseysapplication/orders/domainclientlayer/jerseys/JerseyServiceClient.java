package com.soccerjerseysapplication.orders.domainclientlayer.jerseys;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.orders.utils.HttpErrorInfo;
import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public void updateJerseyQuantity(String jerseyId, int quantityChange) {
        String url = JERSEY_SERVICE_BASE_URL + "/adjust-stock/" + jerseyId;
        restTemplate.postForObject(url, quantityChange, Void.class);
    }


    public String getErrorMessage(HttpClientErrorException ex) {
        try{
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex){
            return ioex.getMessage();
        }
    }
    private RuntimeException handleHttpClientException(HttpClientErrorException ex){
        //adding all the exceptions that the service throws
        if (ex.getStatusCode() == NOT_FOUND)
            return new NotFoundException(getErrorMessage(ex));
        return ex;
    }

}


//package com.soccerjerseysapplication.orders.domainclientlayer.jerseys;
//
//import com.soccerjerseysapplication.orders.domainclientlayer.BaseServiceClient;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Slf4j
//@Component
//public class JerseyServiceClient extends BaseServiceClient {
//
//    private final String jerseyServiceBaseUrl;
//
//    public JerseyServiceClient(RestTemplate restTemplate, ObjectMapper objectMapper,
//                               @Value("${app.jerseys-service.host}") String jerseysServiceHost,
//                               @Value("${app.jerseys-service.port}") String jerseysServicePort) {
//        super(restTemplate, objectMapper);
//        this.jerseyServiceBaseUrl = "http://" + jerseysServiceHost + ":" + jerseysServicePort + "/api/v1/jerseys";
//    }
//
//    public JerseyResponseModel getJerseyById(String jerseyId) {
//        String url = jerseyServiceBaseUrl + "/" + jerseyId;
//        return getForObject(url, JerseyResponseModel.class);
//    }
//}
