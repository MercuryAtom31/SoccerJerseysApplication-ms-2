package com.soccerjerseysapplication.teams.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseModel {

    private String teamId;
    private String name;
    private String country;
}
