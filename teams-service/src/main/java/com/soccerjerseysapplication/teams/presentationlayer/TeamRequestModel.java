package com.soccerjerseysapplication.teams.presentationlayer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class TeamRequestModel {

    @NotNull
    private String name;
    @NotNull
    private String country;
}
