package com.soccerjerseysapplication.teams.mapperlayer;


import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.teams.presentationlayer.TeamRequestModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamIdentifier", ignore = true)
    Team requestModelToEntity(TeamRequestModel requestModel, TeamIdentifier teamIdentifier);
}

