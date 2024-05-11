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
    public List<OrderResponseModel> getAllOrdersForCustomer(String customerId) {
        return orderResponseMapper.responseModelListToResponseModelList(orderServiceClient.getAllOrdersForCustomer(customerId));
    }

    @Override
    public OrderResponseModel getCustomerOrderByOrderId(String customerId, String orderId) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.getCustomerOrderByOrderId(customerId,orderId));
    }

    @Override
    public OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.createCustomerOrder(customerId,orderRequestModel));
    }

    @Override
    public OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel) {
        return orderResponseMapper.responseModelToResponseModel(orderServiceClient.updateCustomerOrder(customerId,orderId, orderRequestModel));
    }

    @Override
    public void deleteCustomerOrder(String customerId, String orderId) {
        orderServiceClient.deleteCustomerOrder(customerId,orderId);
    }
}
