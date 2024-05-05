package com.soccerjerseysapplication.customers.businesslayer;


import com.soccerjerseysapplication.customers.presentationlayer.CustomerRequestModel;
import com.soccerjerseysapplication.customers.presentationlayer.CustomerResponseModel;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseModel> getCustomers();
    CustomerResponseModel getCustomerByCustomerId(String customerId);
    CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel);
    CustomerResponseModel updateCustomer(CustomerRequestModel updatedCustomer, String customerId);
    void removeCustomer(String customerId);
}
