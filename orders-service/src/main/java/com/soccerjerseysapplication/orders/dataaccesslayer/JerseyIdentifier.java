package com.soccerjerseysapplication.orders.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class JerseyIdentifier {

    private String jerseyId;

    public JerseyIdentifier() {
        this.jerseyId = UUID.randomUUID().toString();
    }

    public JerseyIdentifier(String jerseyId) {
        this.jerseyId = jerseyId;
    }
}
