package com.soccerjerseysapplication.orders.dataaccesslayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderPersistenceTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void setup() {
        // Setup data here
        CustomerIdentifier customerIdentifier = new CustomerIdentifier("customer1");
        JerseyIdentifier jerseyIdentifier = new JerseyIdentifier("jersey1");
        TeamIdentifier teamIdentifier = new TeamIdentifier("team1");
        order = Order.builder()
                .customerIdentifier(customerIdentifier)
                .jerseyIdentifier(jerseyIdentifier)
                .teamIdentifier(teamIdentifier)
                .orderDate("2024-05-01")
                .totalPriceOrder(100.99)
                .build();
        orderRepository.save(order);
    }

    @Test
    public void findOrderByCustomerId_shouldSucceed() {
        List<Order> results = orderRepository.findOrderByCustomerModel_CustomerId("customer1");
        assertFalse(results.isEmpty());
        assertEquals("customer1", results.get(0).getCustomerIdentifier().getCustomerId());
    }

    @Test
    public void findOrderByCustomerId_shouldFailWhenNoOrdersExist() {
        List<Order> results = orderRepository.findOrderByCustomerModel_CustomerId("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    public void findOrdersByCustomerModel_CustomerIdAndOrderIdentifier_OrderId_shouldSucceed() {
        Order foundOrder = orderRepository.findOrdersByCustomerModel_CustomerIdAndOrderIdentifier_OrderId("customer1", order.getOrderIdentifier().getOrderId());
        assertNotNull(foundOrder);
        assertEquals("customer1", foundOrder.getCustomerIdentifier().getCustomerId());
    }

    @Test
    public void findOrdersByCustomerModel_CustomerIdAndOrderIdentifier_OrderId_shouldFailWhenOrderDoesNotExist() {
        Order foundOrder = orderRepository.findOrdersByCustomerModel_CustomerIdAndOrderIdentifier_OrderId("customer1", "nonexistent");
        assertNull(foundOrder);
    }

    @Test
    public void deleteOrder_shouldSucceed() {
        orderRepository.delete(order);
        assertTrue(orderRepository.findOrderByCustomerModel_CustomerId("customer1").isEmpty());
    }
}
