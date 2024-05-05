package com.soccerjerseysapplication.apigateway.presentationlayer.teams;

import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponseModel extends RepresentationModel<TeamResponseModel> {

    private String teamId;
    private String name;
    private String country;
}
