package com.soccerjerseysapplication.jerseys.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.UUID;

@Embeddable
@Getter
public class JerseyIdentifier {

    @UniqueElements
    private String jerseyId;

    public JerseyIdentifier() {
        this.jerseyId = UUID.randomUUID().toString();
    }

    public JerseyIdentifier(String jerseyId) {
        this.jerseyId = jerseyId;
    }
}
