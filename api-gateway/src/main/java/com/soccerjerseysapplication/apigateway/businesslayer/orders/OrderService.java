package com.soccerjerseysapplication.apigateway.businesslayer.orders;

import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;

import java.util.List;

public interface OrderService {

    List<OrderResponseModel> getAllOrders();
    OrderResponseModel getOrderByOrderId(String orderId);
    OrderResponseModel createOrder(OrderRequestModel orderRequestModel);
    OrderResponseModel updateOrder(String orderId, OrderRequestModel orderRequestModel);
    void deleteOrder(String orderId);
}
