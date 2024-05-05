package com.soccerjerseysapplication.orders.domainclientlayer.jerseys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JerseyResponseModel {

    //private Integer id;

    private String jerseyId;

    private String size;

    private String color;

    private String styles;

    private Integer quantity;

    private Double price;
}
