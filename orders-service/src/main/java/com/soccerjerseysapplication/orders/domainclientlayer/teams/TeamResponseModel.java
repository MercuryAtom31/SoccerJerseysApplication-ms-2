package com.soccerjerseysapplication.orders.domainclientlayer.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
//@AllArgsConstructor
@AllArgsConstructor
public class TeamResponseModel {

    private String teamId;
    private String name;
    private String country;
}
