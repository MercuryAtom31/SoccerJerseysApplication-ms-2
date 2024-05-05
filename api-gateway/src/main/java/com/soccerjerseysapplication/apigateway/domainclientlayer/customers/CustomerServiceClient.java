package com.soccerjerseysapplication.apigateway.domainclientlayer.customers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
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

@Component
@Slf4j
public class CustomerServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CUSTOMER_SERVICE_BASE_URL;

    public CustomerServiceClient(RestTemplate restTemplate,
                                 ObjectMapper mapper,
                                 @Value("${app.customers-service.host}") String customerServiceHost,
                                 @Value("${app.customers-service.port}") String customerServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;

        CUSTOMER_SERVICE_BASE_URL  = "http://" + customerServiceHost + ":" + customerServicePort + "/api/v1/customers";
    }

    public List<CustomerResponseModel> getAllCustomers() {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL;

            CustomerResponseModel[] customerResponseModel = restTemplate.getForObject(url, CustomerResponseModel[].class);

            return Arrays.asList(customerResponseModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;

            CustomerResponseModel customerResponseModel = restTemplate.getForObject(url, CustomerResponseModel.class);

            return customerResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL;

            CustomerResponseModel customerResponseModel = restTemplate.postForObject(url, customerRequestModel, CustomerResponseModel.class);

            return customerResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;

            restTemplate.put(url, customerRequestModel);

            return getCustomerByCustomerId(customerId);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteCustomer(String customerId) {
        try {
            String url = CUSTOMER_SERVICE_BASE_URL + "/" + customerId;

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
