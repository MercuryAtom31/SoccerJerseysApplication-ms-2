package com.soccerjerseysapplication.orders.domainclientlayer.jerseys;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JerseyRequestModel {

    @NotNull
    private String size;

    @NotNull
    private String color;

    @NotNull
    private String styles;

    @NotNull
    private Integer quantity;

    @NotNull
    private Double price;
}
