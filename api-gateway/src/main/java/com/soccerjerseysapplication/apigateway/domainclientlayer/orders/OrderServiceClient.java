package com.soccerjerseysapplication.apigateway.domainclientlayer.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
import com.soccerjerseysapplication.apigateway.utils.HttpErrorInfo;
import com.soccerjerseysapplication.apigateway.utils.exceptions.InvalidInputException;
import com.soccerjerseysapplication.apigateway.utils.exceptions.ItemUnavailableException;
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
public class OrderServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String ORDER_SERVICE_BASE_URL;

    public OrderServiceClient(RestTemplate restTemplate,
                              ObjectMapper mapper,
                              @Value("${app.orders-service.host}") String orderServiceHost,
                              @Value("${app.orders-service.port}") String orderServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = mapper;

        ORDER_SERVICE_BASE_URL  = "http://" + orderServiceHost + ":" + orderServicePort + "/api/v1/customers/";
    }

    public List<OrderResponseModel>getAllOrdersForCustomer(String customerId) {
        try {
            String url = ORDER_SERVICE_BASE_URL + customerId + "/orders";

            OrderResponseModel[] orderResponseModel = restTemplate.getForObject(url, OrderResponseModel[].class);

            return Arrays.asList(orderResponseModel);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public OrderResponseModel getCustomerOrderByOrderId(String customerId, String orderId) {
        try {
            //String url = ORDER_SERVICE_BASE_URL + "/" + orderId;
            //String url = ORDER_SERVICE_BASE_URL + orderId;
            String url = ORDER_SERVICE_BASE_URL + "/" + customerId + "/" + orderId;

            OrderResponseModel orderResponseModel = restTemplate.getForObject(url, OrderResponseModel.class);

            return orderResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel) {
        try {
//            String url = ORDER_SERVICE_BASE_URL;
            String url = ORDER_SERVICE_BASE_URL + customerId + "/orders";

            OrderResponseModel orderResponseModel = restTemplate.postForObject(url, orderRequestModel, OrderResponseModel.class);

            return orderResponseModel;
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel) {
        try {
            //String url = ORDER_SERVICE_BASE_URL + "/" + orderId;
            //String url = ORDER_SERVICE_BASE_URL + orderId;
            String url = ORDER_SERVICE_BASE_URL + "/" + customerId + "/orders/" + orderId;

            restTemplate.put(url, orderRequestModel);

            return getCustomerOrderByOrderId(customerId, orderId);
        }
        catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteCustomerOrder(String customerId, String orderId) {
        try {
            //String url = ORDER_SERVICE_BASE_URL + "/" + orderId;
            //String url = ORDER_SERVICE_BASE_URL + orderId;
            String url = ORDER_SERVICE_BASE_URL + "/" + customerId + "/orders/" + orderId;

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
            return new ItemUnavailableException(getErrorMessage(ex));
        }
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
