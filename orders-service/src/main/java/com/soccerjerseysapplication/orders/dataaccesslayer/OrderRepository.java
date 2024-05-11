package com.soccerjerseysapplication.orders.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {


    Order findOrderByCustomerIdentifier_CustomerIdAndOrderIdentifier_OrderId(String customerId, String orderId);
    List<Order> findAllByCustomerIdentifier_CustomerId(String customerId);



    //OLD CODE
//    List<Order> findByCustomerName(String customerName);
//
//    // Find an order by its identifier
//    Order findOrderByOrderIdentifier(String orderIdentifier);
//
//    // Find orders by customer name
//    List<Order> findOrdersByCustomerName(String customerName);
//
//    // Find orders by status
//    List<Order> findOrdersByStatus(String status);
//
//    // Find orders with a total amount greater than a given value
//    List<Order> findOrdersByTotalAmountGreaterThan(Double totalAmount);
//
//    // If you have a separate embedded identifier class
//    Order findByOrderIdentifier_OrderId(String orderId);
}
