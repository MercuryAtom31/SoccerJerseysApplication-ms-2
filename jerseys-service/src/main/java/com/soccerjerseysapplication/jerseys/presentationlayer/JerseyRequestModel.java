package com.soccerjerseysapplication.jerseys.presentationlayer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
