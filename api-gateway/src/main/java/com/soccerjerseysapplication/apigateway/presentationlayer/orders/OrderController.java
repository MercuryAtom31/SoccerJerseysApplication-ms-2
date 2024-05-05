package com.soccerjerseysapplication.apigateway.presentationlayer.orders;

import com.soccerjerseysapplication.apigateway.businesslayer.orders.OrderService;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
import com.soccerjerseysapplication.apigateway.businesslayer.orders.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(
            value = "",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<OrderResponseModel>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping(
            value = "/{orderId}",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseModel> getOrderByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok().body(orderService.getOrderByOrderId(orderId));
    }

    @PostMapping(
            value = "",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<OrderResponseModel> createOrder(@RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.ok().body(orderService.createOrder(orderRequestModel));
    }

    @PutMapping(
            value = "/{orderId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<OrderResponseModel> updateOrder(@PathVariable String orderId, @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, orderRequestModel));
    }

    @DeleteMapping(
            value = "/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}