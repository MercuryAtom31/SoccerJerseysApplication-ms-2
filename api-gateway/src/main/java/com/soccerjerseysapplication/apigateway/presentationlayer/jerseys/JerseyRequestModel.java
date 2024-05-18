package com.soccerjerseysapplication.apigateway.presentationlayer.jerseys;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@AllArgsConstructor
public class JerseyRequestModel {

    private String size;
    private String color;
    private String styles;
    private Integer quantity;
    private Double price;
}
