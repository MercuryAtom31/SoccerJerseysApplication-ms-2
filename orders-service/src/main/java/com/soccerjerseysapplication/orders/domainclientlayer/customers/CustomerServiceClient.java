package com.soccerjerseysapplication.orders.domainclientlayer.customers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.orders.utils.HttpErrorInfo;
import com.soccerjerseysapplication.customers.utils.exceptions.NotFoundException;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String  CUSTOMER_SERVICE_BASE_URL;

    public CustomerServiceClient(RestTemplate restTemplate,
                                 ObjectMapper objectMapper,
                                 @Value("${app.customers-service.host}") String customerServiceHost,
                                 @Value("${app.customers-service.port}") String customerServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CUSTOMER_SERVICE_BASE_URL = "http://" + customerServiceHost + ":" + customerServicePort + "/api/v1/customers";;
    }

    //Previously named: getCustomerById
    public CustomerResponseModel getCustomerByCustomerId(String customerId){
        try{
            String url = CUSTOMER_SERVICE_BASE_URL + "/"+ customerId;
            return restTemplate.getForObject(url, CustomerResponseModel.class);
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
    private RuntimeException handleHttpClientException(HttpClientErrorException ex){
        //Adding all the exceptions that the customers-service methods throw.
        if (ex.getStatusCode() == NOT_FOUND)
            return new NotFoundException(getErrorMessage(ex));
        return ex;
    }
}