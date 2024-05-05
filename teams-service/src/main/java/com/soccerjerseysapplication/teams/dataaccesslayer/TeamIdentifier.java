package com.soccerjerseysapplication.teams.dataaccesslayer;


import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.UUID;

@Getter
public class TeamIdentifier {

    @Indexed(unique = true)
    private String teamId;

    public TeamIdentifier() {
        this.teamId = UUID.randomUUID().toString();
    }

    public TeamIdentifier(String teamId) {
        this.teamId = teamId;
    }
}
