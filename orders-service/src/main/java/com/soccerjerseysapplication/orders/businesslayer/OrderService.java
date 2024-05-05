package com.soccerjerseysapplication.orders.businesslayer;

//import com.soccerjerseysapplication.orders.dataaccesslayer.Order;
import com.soccerjerseysapplication.orders.presentationlayer.OrderRequestModel;
import com.soccerjerseysapplication.orders.presentationlayer.OrderResponseModel;
import com.soccerjerseysapplication.orders.dataaccesslayer.CustomerIdentifier;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    //OLD CODE

//    OrderResponseModel getCustomerOrderByOrderId(String orderId, String customerId);
//
//    OrderResponseModel createOrder(OrderRequestModel requestModel, String customerId);
//    OrderResponseModel getOrderById(String orderId);
//    OrderResponseModel updateOrder(OrderRequestModel requestModel, String orderId);
//    void deleteOrder(String orderId);
//    List<OrderResponseModel> getOrders();
//
//    List<OrderResponseModel> getOrdersByCustomerId(String customerId);
//
//    OrderResponseModel processCustomerOrder(OrderRequestModel orderRequestModel, String customerId);
//
//    //void cancelOrder(String orderId);

//NEW CODE
    List<OrderResponseModel> getAllOrdersForCustomer(String customerId);
    OrderResponseModel getCustomerOrderByOrderId(String customerId, String orderId);
    OrderResponseModel createCustomerOrder(String customerId, OrderRequestModel orderRequestModel);
    OrderResponseModel updateCustomerOrder(String customerId, String orderId, OrderRequestModel orderRequestModel);
    void deleteCustomerOrder(String customerId, String orderId);

}
