package com.soccerjerseysapplication.orders.dataaccesslayer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class OrderIdentifier {
    private String orderId;

    public OrderIdentifier() {
        this.orderId = UUID.randomUUID().toString();
    }
}
