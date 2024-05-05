package com.soccerjerseysapplication.apigateway.presentationlayer.customers;

import com.soccerjerseysapplication.apigateway.businesslayer.customers.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(
            value = "",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CustomerResponseModel>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @GetMapping(
            value = "/{customerId}",
            produces = "application/json"
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerResponseModel> getCustomerByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerService.getCustomerByCustomerId(customerId));
    }

    @PostMapping(
            value = "",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<CustomerResponseModel> createCustomer(@RequestBody CustomerRequestModel customerRequestModel) {
        return ResponseEntity.ok().body(customerService.createCustomer(customerRequestModel));
    }

    @PutMapping(
            value = "/{customerId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<CustomerResponseModel> updateCustomer(@PathVariable String customerId, @RequestBody CustomerRequestModel customerRequestModel) {
        return ResponseEntity.ok().body(customerService.updateCustomer(customerId, customerRequestModel));
    }

    @DeleteMapping(
            value = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}