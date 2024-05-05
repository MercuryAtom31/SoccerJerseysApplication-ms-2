package com.soccerjerseysapplication.jerseys.presentationlayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JerseyResponseModel {

    //private Integer id;

    private String jerseyId;

    private String size;

    private String color;

    private String styles;

    private Integer quantity;

    private Double price;

    // New
    //private String customerName;
    //private String orderDate;
}
