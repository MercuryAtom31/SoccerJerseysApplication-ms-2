//package com.soccerjerseysapplication.apigateway.domainclientlayer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.soccerjerseysapplication.apigateway.domainclientlayer.orders.OrderServiceClient;
//import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
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
//import java.util.List;
//
//@ExtendWith(MockitoExtension.class)
//public class OrderServiceClientTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private OrderServiceClient orderServiceClient;
//
//    private final String baseUrl = "http://localhost:8080/api/v1/orders";
//
//    @BeforeEach
//    void setup() {
//        orderServiceClient = new OrderServiceClient(restTemplate, "localhost", "8080");
//    }
//
//    @Test
//    void getAllOrders_shouldReturnOrders() {
//        OrderResponseModel[] ordersArray = {
//                new OrderResponseModel("customerName", "customerId", "orderId", "orderDate", 120.00, "teamId", "jerseyId", 60.00, "M", "Red", "Striped", "teamName"),
//                new OrderResponseModel("customerName2", "customerId2", "orderId2", "orderDate2", 150.00, "teamId2", "jerseyId2", 70.00, "L", "Blue", "Solid", "teamName2")
//        };
//        given(restTemplate.getForObject(baseUrl, OrderResponseModel[].class)).willReturn(ordersArray);
//
//        List<OrderResponseModel> orders = orderServiceClient.getAllOrders();
//
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//        assertEquals("orderId", orders.get(0).getOrderIdentifier());
//        verify(restTemplate, times(1)).getForObject(baseUrl, OrderResponseModel[].class);
//    }
//
//    @Test
//    void getOrderByOrderId_shouldReturnOrder() {
//        OrderResponseModel expectedOrder = new OrderResponseModel("customerName", "customerId", "orderId", "orderDate", 120.00, "teamId", "jerseyId", 60.00, "M", "Red", "Striped", "teamName");
//        given(restTemplate.getForObject(baseUrl + "/1", OrderResponseModel.class)).willReturn(expectedOrder);
//
//        OrderResponseModel actualOrder = orderServiceClient.getOrderByOrderId("1");
//
//        assertNotNull(actualOrder);
//        assertEquals("orderId", actualOrder.getOrderIdentifier());
//        verify(restTemplate, times(1)).getForObject(baseUrl + "/1", OrderResponseModel.class);
//    }
//
//    @Test
//    void getOrderByOrderId_whenNotFound_shouldThrowException() {
//        given(restTemplate.getForObject(baseUrl + "/999", OrderResponseModel.class))
//                .willThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//        assertThrows(NotFoundException.class, () -> orderServiceClient.getOrderByOrderId("999"));
//    }
//}
//
