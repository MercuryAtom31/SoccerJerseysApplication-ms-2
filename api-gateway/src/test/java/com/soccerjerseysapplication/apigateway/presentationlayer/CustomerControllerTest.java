package com.soccerjerseysapplication.apigateway.presentationlayer;

import com.soccerjerseysapplication.apigateway.businesslayer.customers.CustomerService;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerController;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(CustomerController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        // Setup for tests
    }

    @Test
    void getAllCustomers_shouldReturnCustomers() throws Exception {
        List<CustomerResponseModel> customers = List.of(
                new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null)
        );
        given(customerService.getAllCustomers()).willReturn(customers);

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void getCustomerByCustomerId_shouldReturnCustomer() throws Exception {
        CustomerResponseModel customer = new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
        given(customerService.getCustomerByCustomerId("1")).willReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void createCustomer_shouldCreateAndReturnCustomer() throws Exception {
        CustomerRequestModel requestModel = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
        CustomerResponseModel responseModel = new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);

        given(customerService.createCustomer(requestModel)).willReturn(responseModel);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"emailAddress\":\"john.doe@example.com\",\"streetAddress\":\"1234 Street\",\"city\":\"City\",\"province\":\"State\",\"country\":\"Country\",\"postalCode\":\"PostalCode\",\"phoneNumbers\":[]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
}














//package com.soccerjerseysapplication.apigateway.presentationlayer;
//
//import com.soccerjerseysapplication.apigateway.businesslayer.customers.CustomerService;
//import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerController;
//import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerRequestModel;
//import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//@WebMvcTest(CustomerController.class)
//public class CustomerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CustomerService customerService;
//
//    @BeforeEach
//    void setup() {
//
//    }
//
//    @Test
//    void getAllCustomers_shouldReturnCustomers() throws Exception {
//        List<CustomerResponseModel> customers = Collections.singletonList(
//                new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null)
//        );
//        given(customerService.getAllCustomers()).willReturn(customers);
//
//        mockMvc.perform(get("/api/v1/customers")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].firstName").value("John"));
//    }
//
//    @Test
//    void getCustomerByCustomerId_whenCustomerExists_shouldReturnCustomer() throws Exception {
//        CustomerResponseModel customer = new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
//        given(customerService.getCustomerByCustomerId("1")).willReturn(customer);
//
//        mockMvc.perform(get("/api/v1/customers/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John"));
//    }
//
//    @Test
//    void createCustomer_shouldCreateAndReturnCustomer() throws Exception {
//        CustomerRequestModel requestModel = new CustomerRequestModel("John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
//        CustomerResponseModel responseModel = new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
//
//        given(customerService.createCustomer(requestModel)).willReturn(responseModel);
//
//        mockMvc.perform(post("/api/v1/customers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"emailAddress\":\"john.doe@example.com\",\"streetAddress\":\"1234 Street\",\"city\":\"City\",\"province\":\"State\",\"country\":\"Country\",\"postalCode\":\"PostalCode\",\"phoneNumbers\":[]}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName").value("John"));
//    }
//}
