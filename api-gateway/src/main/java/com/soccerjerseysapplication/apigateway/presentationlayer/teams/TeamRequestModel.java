package com.soccerjerseysapplication.apigateway.presentationlayer.teams;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@AllArgsConstructor
public class TeamRequestModel {

    private String name;
    private String country;
}
