package com.soccerjerseysapplication.teams.mapperlayer;

import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.presentationlayer.TeamResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResponseMapper {
    @Mappings({
            @Mapping(target = "teamId", expression = "java(team.getTeamIdentifier().getTeamId())")
    })
    TeamResponseModel entityToResponseModel(Team team);

    List<TeamResponseModel> entityListToResponseModelList(List<Team> teams);
}

// REMINDER: Mapper creates the DTO.
