package com.soccerjerseysapplication.apigateway.datamapperlayer.teams;

import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamController;
import com.soccerjerseysapplication.apigateway.presentationlayer.teams.TeamResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TeamResponseMapper {

    TeamResponseModel responseModelToResponseModel(TeamResponseModel teamResponseModel);

    List<TeamResponseModel> responseModelListToResponseModelList(List<TeamResponseModel> teamResponseModelList);

    @AfterMapping
    default void addLinks(@MappingTarget TeamResponseModel teamResponseModel) {
        // teamLink
        Link selfLink = linkTo(methodOn(TeamController.class)
                .getTeamByTeamId(teamResponseModel.getTeamId()))
                .withSelfRel();
        teamResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(TeamController.class)
                .getAllTeams())
                .withRel("all teams");
        teamResponseModel.add(allLink);
    }
}
