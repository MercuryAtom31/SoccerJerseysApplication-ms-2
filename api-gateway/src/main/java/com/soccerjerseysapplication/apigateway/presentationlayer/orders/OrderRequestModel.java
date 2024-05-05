package com.soccerjerseysapplication.apigateway.presentationlayer.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class OrderRequestModel {

    String teamIdentifier;
    String jerseyIdentifier;
//    private String customerName;
//    private String productName;
//    private int quantity;
}
