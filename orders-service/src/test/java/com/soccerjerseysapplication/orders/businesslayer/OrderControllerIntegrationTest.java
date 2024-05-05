package com.soccerjerseysapplication.orders.businesslayer;

import com.soccerjerseysapplication.orders.businesslayer.OrderService;
import com.soccerjerseysapplication.orders.dataaccesslayer.Order;
import com.soccerjerseysapplication.orders.presentationlayer.OrderController;
import com.soccerjerseysapplication.orders.presentationlayer.OrderRequestModel;
import com.soccerjerseysapplication.orders.presentationlayer.OrderResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @Autowired
    private WebTestClient webTestClient;
    private OrderResponseModel orderResponse;

    @BeforeEach
    public void setup() {
        orderResponse = new OrderResponseModel("John Doe", "customer1",
                "order1", "2024-05-01",
                100.0, "team1",
                "jersey1", 49.99,
                "Red", "Sport", "Long Sleeves",
                "Manchester United");
        //Order saveOrder = new Order()
    }

    @Test
    public void getAllOrdersForCustomer_shouldReturnOrders() throws Exception {
        Integer expectedOrderCount = 1;
        webTestClient.get()
                .uri("/api/v1/customers/customer1/orders")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(expectedOrderCount);
       // List<OrderResponseModel> orders = Arrays.asList(orderResponse);
      //  given(orderService.getAllOrdersForCustomer("customer1")).willReturn(orders);
    }

    @Test
    public void getOrderById_shouldReturnOrder() throws Exception {
        given(orderService.getCustomerOrderByOrderId("customer1", "order1")).willReturn(orderResponse);

        mockMvc.perform(get("/api/v1/customers/customer1/orders/order1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdentifier").value("order1"));
    }

    @Test
    public void createOrder_shouldSucceed() throws Exception {
        given(orderService.createCustomerOrder(eq("customer1"), any(OrderRequestModel.class))).willReturn(orderResponse);

        mockMvc.perform(post("/api/v1/customers/customer1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamIdentifier\": \"team1\", \"jerseyIdentifier\": \"jersey1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerIdentifier").value("customer1"));
    }

    @Test
    public void updateOrder_shouldSucceed() throws Exception {
        given(orderService.updateCustomerOrder(eq("customer1"), eq("order1"), any(OrderRequestModel.class))).willReturn(orderResponse);

        mockMvc.perform(put("/api/v1/customers/customer1/orders/order1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"teamIdentifier\": \"team1\", \"jerseyIdentifier\": \"jersey1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdentifier").value("order1"));
    }

    @Test
    public void deleteOrder_shouldSucceed() throws Exception {
        willDoNothing().given(orderService).deleteCustomerOrder("customer1", "order1");

        mockMvc.perform(delete("/api/v1/customers/customer1/orders/order1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}

