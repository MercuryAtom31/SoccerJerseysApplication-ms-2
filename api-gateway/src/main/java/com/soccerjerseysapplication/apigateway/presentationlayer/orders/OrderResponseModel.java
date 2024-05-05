package com.soccerjerseysapplication.apigateway.presentationlayer.orders;

import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.*;

import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseModel extends RepresentationModel<OrderResponseModel> {


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
//    private String orderId;
//    private String customerName;
//    private String productName;
//    private int quantity;
}
