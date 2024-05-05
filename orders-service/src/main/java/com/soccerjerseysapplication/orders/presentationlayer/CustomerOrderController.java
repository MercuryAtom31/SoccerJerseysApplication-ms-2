package com.soccerjerseysapplication.orders.presentationlayer;

import com.soccerjerseysapplication.orders.businesslayer.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/customers/{customerId}/orders")
public class CustomerOrderController {

    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<OrderResponseModel>> getAllOrdersForCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok().body(orderService.getAllOrdersForCustomer(customerId));
    }

    @GetMapping(value = "{orderId}", produces = "application/json")
    public ResponseEntity<OrderResponseModel> getCustomerOrderByOrderId(@PathVariable String customerId,
                                                                         @PathVariable String orderId) {
        return ResponseEntity.ok().body(orderService.getCustomerOrderByOrderId(customerId, orderId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderResponseModel> createCustomerOrder(@PathVariable String customerId,
                                                                    @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createCustomerOrder(customerId, orderRequestModel));
    }

    @PutMapping(value = "{orderId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderResponseModel> updateCustomerOrder(@PathVariable String customerId,
                                                                    @PathVariable String orderId,
                                                                    @RequestBody OrderRequestModel orderRequestModel) {
        return ResponseEntity.ok().body(orderService.updateCustomerOrder(customerId, orderId, orderRequestModel));
    }

    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteCustomerOrder(@PathVariable String customerId,
                                                       @PathVariable String orderId) {
        orderService.deleteCustomerOrder(customerId, orderId);
        return ResponseEntity.noContent().build();
    }

    //OLD CODIGO
//    private final OrderService orderService;
//
//    @PostMapping(
//            produces = "application/json"
//    )
//    ResponseEntity<OrderResponseModel> processCustomerOrder(
//            @RequestBody OrderRequestModel orderRequestModel,
//            @PathVariable String customerId){
//        return ResponseEntity.status(CREATED).body(orderService.processCustomerOrder(orderRequestModel, customerId));
//    }
//
//    @GetMapping(
//            value = "/{orderId}",
//            produces = "application/json"
//    )
//    ResponseEntity<OrderResponseModel> getCustomerPurchaseByPurchaseId(@PathVariable String orderId, @PathVariable String customerId){
//        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
//    }
//
////    @GetMapping(
////            value = "/{purchaseId}",
////            produces = "application/json"
////    )
////    ResponseEntity<PurchaseOrderResponseModel> getClientPurchaseByPurchaseId(@PathVariable String purchaseId, @PathVariable String clientId){
////        return ResponseEntity.ok().body(purchaseOrderService.getClientPurchaseByPurchaseId(purchaseId, clientId));
////    }
}
