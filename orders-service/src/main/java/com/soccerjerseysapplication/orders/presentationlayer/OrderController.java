//package com.soccerjerseysapplication.orders.presentationlayer;
//
//
//import com.soccerjerseysapplication.orders.businesslayer.OrderService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("api/v1/customers/{customerId}/orders")
//public class OrderController {
//
//    private final OrderService orderService;
//
//
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<OrderResponseModel>> getAllOrders(@PathVariable String customerId) {
//        return ResponseEntity.ok().body(orderService.getAllOrdersForCustomer(customerId));
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponseModel> getOrderById(@PathVariable String customerId,@PathVariable String orderId) {
//        OrderResponseModel order = orderService.getCustomerOrderByOrderId(customerId, orderId);
//        if (order != null) {
//            return ResponseEntity.ok().body(order);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping()
//    public ResponseEntity<OrderResponseModel> createOrder(@RequestBody OrderRequestModel orderRequestModel, @PathVariable String customerId) {
//        OrderResponseModel createdOrder = orderService.createCustomerOrder(customerId, orderRequestModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
//    }
//
//    @PutMapping("/{orderId}")
//    public ResponseEntity<OrderResponseModel> updateOrder(@PathVariable String customerId, @RequestBody OrderRequestModel orderRequestModel, @PathVariable String orderId) {
//        OrderResponseModel updatedOrder = orderService.updateCustomerOrder(customerId, orderId, orderRequestModel);
//        if (updatedOrder != null) {
//            return ResponseEntity.ok().body(updatedOrder);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable String customerId, @PathVariable String orderId) {
//        orderService.deleteCustomerOrder(customerId, orderId);
//        return ResponseEntity.noContent().build();
//    }
//}