package com.soccerjerseysapplication.apigateway.businesslayer.customers;

import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseModel> getAllCustomers();
    CustomerResponseModel getCustomerByCustomerId(String customerId);
    CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel);
    CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel);
    void deleteCustomer(String customerId);
}