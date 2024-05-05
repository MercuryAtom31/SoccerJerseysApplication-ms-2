package com.soccerjerseysapplication.orders.dataaccesslayer;


import lombok.Getter;
import org.springframework.stereotype.Indexed;
//import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Getter
public class TeamIdentifier {
    private String teamId;

    public TeamIdentifier() {
        this.teamId = UUID.randomUUID().toString();
    }

    public TeamIdentifier(String teamId) {
        this.teamId = teamId;
    }
}
