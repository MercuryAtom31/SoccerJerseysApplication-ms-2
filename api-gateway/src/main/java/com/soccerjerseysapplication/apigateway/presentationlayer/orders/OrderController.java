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
@RequestMapping("/api/v1/customers/{customerId}/orders")
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
    public ResponseEntity<List<OrderResponseModel>> getAllOrdersForCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok().body(orderService.getAllOrdersForCustomer(customerId));
    }

    @GetMapping(
            value = "/{orderId}",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseModel> getCustomerOrderByOrderId(@PathVariable String customerId,
                                                                        @PathVariable String orderId) {
        return ResponseEntity.ok().body(orderService.getCustomerOrderByOrderId(customerId, orderId));
    }

    @PostMapping(
            value = "",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<OrderResponseModel> createCustomerOrder(@PathVariable String customerId,
                                                                  @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.ok().body(orderService.createCustomerOrder(customerId, orderRequestModel));
    }

    @PutMapping(
            value = "/{orderId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<OrderResponseModel> updateCustomerOrder(@PathVariable String customerId,
                                                                  @PathVariable String orderId,
                                                                  @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.ok().body(orderService.updateCustomerOrder(customerId,orderId, orderRequestModel));
    }

    @DeleteMapping(
            value = "/{orderId}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable String customerId,
                                                    @PathVariable String orderId) {
        orderService.deleteCustomerOrder(customerId, orderId);
        return ResponseEntity.noContent().build();
    }
}