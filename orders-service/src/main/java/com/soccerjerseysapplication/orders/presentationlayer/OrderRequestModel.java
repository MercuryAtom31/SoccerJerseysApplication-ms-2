package com.soccerjerseysapplication.orders.presentationlayer;

import com.soccerjerseysapplication.orders.dataaccesslayer.JerseyIdentifier;
import com.soccerjerseysapplication.orders.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestModel {
    String teamIdentifier;
    String jerseyIdentifier;
    private int quantity;  // User-defined quantity for the order
}
