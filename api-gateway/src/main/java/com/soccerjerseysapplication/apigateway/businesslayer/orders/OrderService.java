package com.soccerjerseysapplication.apigateway.businesslayer.orders;

import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;

import java.util.List;

public interface OrderService {

    List<OrderResponseModel> getAllOrdersForCustomer(String customerId);
    OrderResponseModel getCustomerOrderByOrderId(String customerId, String orderId);
    OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel);
    OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel);
    void deleteCustomerOrder(String customerId, String orderId);
}
