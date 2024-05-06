//package com.soccerjerseysapplication.apigateway.domainclientlayer;
//
//import com.soccerjerseysapplication.apigateway.domainclientlayer.customers.CustomerServiceClient;
//import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
//import com.soccerjerseysapplication.apigateway.utils.exceptions.NotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//@ExtendWith(MockitoExtension.class)
//public class CustomerServiceClientTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private CustomerServiceClient customerServiceClient;
//
//    private final String baseUrl = "http://localhost:8080/api/v1/customers";
//
//    @BeforeEach
//    void setup() {
//        customerServiceClient = new CustomerServiceClient(restTemplate, "localhost", "8080");
//    }
//
//    @Test
//    void getAllCustomers_shouldReturnCustomers() {
//        CustomerResponseModel[] customersArray = {
//                new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null),
//                new CustomerResponseModel("2", "Jane", "Doe", "jane.doe@example.com", "1235 Street", "City", "State", "Country", "PostalCode", null)
//        };
//        given(restTemplate.getForObject(baseUrl, CustomerResponseModel[].class)).willReturn(customersArray);
//
//        CustomerResponseModel[] customers = customerServiceClient.getAllCustomers().toArray(new CustomerResponseModel[0]);
//
//        assertNotNull(customers);
//        assertEquals(2, customers.length);
//        assertEquals("John", customers[0].getFirstName());
//        verify(restTemplate, times(1)).getForObject(baseUrl, CustomerResponseModel[].class);
//    }
//
//    @Test
//    void getCustomerByCustomerId_shouldReturnCustomer() {
//        CustomerResponseModel expectedCustomer = new CustomerResponseModel("1", "John", "Doe", "john.doe@example.com", "1234 Street", "City", "State", "Country", "PostalCode", null);
//        given(restTemplate.getForObject(baseUrl + "/1", CustomerResponseModel.class)).willReturn(expectedCustomer);
//
//        CustomerResponseModel actualCustomer = customerServiceClient.getCustomerByCustomerId("1");
//
//        assertNotNull(actualCustomer);
//        assertEquals("John", actualCustomer.getFirstName());
//        verify(restTemplate, times(1)).getForObject(baseUrl + "/1", CustomerResponseModel.class);
//    }
//
//    @Test
//    void getCustomerByCustomerId_whenNotFound_shouldThrowException() {
//        given(restTemplate.getForObject(baseUrl + "/999", CustomerResponseModel.class))
//                .willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//        assertThrows(NotFoundException.class, () -> customerServiceClient.getCustomerByCustomerId("999"));
//    }
//}
//
