package com.soccerjerseysapplication.apigateway.businesslayer.orders;

import com.soccerjerseysapplication.apigateway.datamapperlayer.orders.OrderResponseMapper;
import com.soccerjerseysapplication.apigateway.domainclientlayer.orders.OrderServiceClient;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderRequestModel;

import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderServiceClient orderServiceClient;
    private final OrderResponseMapper orderResponseMapper;

    public OrderServiceImpl(OrderServiceClient orderServiceClient, OrderResponseMapper orderResponseMapper) {
        this.orderServiceClient = orderServiceClient;
        this.orderResponseMapper = orderResponseMapper;
    }

    @Override
    public List<OrderResponseModel> getAllOrders() {
        return orderResponseMapper.responseModelListToResponseModelList(orderServiceClient.getAllOrders());
    }

    @Override
    public OrderResponseModel getOrderByOrderId(String orderId) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.getOrderByOrderId(orderId));
    }

    @Override
    public OrderResponseModel createOrder(OrderRequestModel orderRequestModel) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.createOrder(orderRequestModel));
    }

    @Override
    public OrderResponseModel updateOrder(String orderId, OrderRequestModel orderRequestModel) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.updateOrder(orderId, orderRequestModel));
    }

    @Override
    public void deleteOrder(String orderId) {
        orderServiceClient.deleteOrder(orderId);
    }
}
