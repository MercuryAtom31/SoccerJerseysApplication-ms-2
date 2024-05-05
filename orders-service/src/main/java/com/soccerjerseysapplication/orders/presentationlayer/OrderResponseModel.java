package com.soccerjerseysapplication.orders.presentationlayer;

import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyResponseModel;
import com.soccerjerseysapplication.orders.domainclientlayer.teams.TeamResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseModel {

    private String customerName;
    private String customerIdentifier;

    private String orderIdentifier;
    private String orderDate;//
    private double totalPriceOrder;//

    private String teamIdentifier;
    private String jerseyIdentifier;

    //Fields from jerseys-service's entity class.
    private double jerseyPrice;
    private String size;
    private String color;
    private String styles;

    //Fields from teams-service's entity class.
    private String teamName;
    //private String country;


}
