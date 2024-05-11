package com.soccerjerseysapplication.apigateway.presentationlayer;

import com.soccerjerseysapplication.apigateway.businesslayer.orders.OrderService;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderController;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
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

@WebMvcTest(OrderController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setup() {

    }

    @Test
    void getAllOrders_shouldReturnOrders() throws Exception {
        List<OrderResponseModel> orders = List.of(
                new OrderResponseModel("customerName", "customerId", "orderId", "orderDate", 120.00, "teamId", "jerseyId", 60.00, "M", "Red", "Striped", "teamName")
        );
        given(orderService.getAllOrdersForCustomer(orders.get(0).getCustomerIdentifier())).willReturn(orders);

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderIdentifier").value("orderId"));
    }

    @Test
    void getOrderById_shouldReturnOrder() throws Exception {
        OrderResponseModel order = new OrderResponseModel("customerName", "customerId", "orderId", "orderDate", 120.00, "teamId", "jerseyId", 60.00, "M", "Red", "Striped", "teamName");
        given(orderService.getCustomerOrderByOrderId("2","1")).willReturn(order);

        mockMvc.perform(get("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdentifier").value("orderId"));
    }
}

