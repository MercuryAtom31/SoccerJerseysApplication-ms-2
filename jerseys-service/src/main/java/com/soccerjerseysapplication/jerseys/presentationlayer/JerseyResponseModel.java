package com.soccerjerseysapplication.jerseys.presentationlayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JerseyResponseModel {

    private String jerseyId;
    private String size;
    private String color;
    private String styles;
    private Integer quantity;
    private Double price;
}
