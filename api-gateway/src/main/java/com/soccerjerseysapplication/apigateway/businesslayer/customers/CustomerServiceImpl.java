package com.soccerjerseysapplication.apigateway.businesslayer.customers;

import com.soccerjerseysapplication.apigateway.datamapperlayer.customers.CustomerResponseMapper;
import com.soccerjerseysapplication.apigateway.domainclientlayer.customers.CustomerServiceClient;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerServiceClient customerServiceClient;
    private final CustomerResponseMapper customerResponseMapper;

    public CustomerServiceImpl(CustomerServiceClient customerServiceClient, CustomerResponseMapper customerResponseMapper) {
        this.customerServiceClient = customerServiceClient;
        this.customerResponseMapper = customerResponseMapper;
    }

    @Override
    public List<CustomerResponseModel> getAllCustomers() {
        return customerResponseMapper.responseModelListToResponseModelList(customerServiceClient.getAllCustomers());
    }

    @Override
    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        return customerResponseMapper.responseModelToResponseModel(customerServiceClient.getCustomerByCustomerId(customerId));
    }

    @Override
    public CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel) {
        return customerResponseMapper.responseModelToResponseModel(customerServiceClient.createCustomer(customerRequestModel));
    }

    @Override
    public CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel) {
        return customerResponseMapper.responseModelToResponseModel(customerServiceClient.updateCustomer(customerId, customerRequestModel));
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerServiceClient.deleteCustomer(customerId);
    }
}
