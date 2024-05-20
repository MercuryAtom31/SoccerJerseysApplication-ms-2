package com.soccerjerseysapplication.teams.mapperlayer;


import com.soccerjerseysapplication.teams.dataaccesslayer.Team;
import com.soccerjerseysapplication.teams.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.teams.presentationlayer.TeamRequestModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "teamIdentifier", ignore = true)
    @Mapping(target = "teamIdentifier", source = "teamIdentifier")
    Team requestModelToEntity(TeamRequestModel requestModel, TeamIdentifier teamIdentifier);
}

/*
 The @Mapping(target = "teamIdentifier", ignore = true) annotation in the TeamRequestMapper
 is causing the teamIdentifier field to be ignored during the mapping process,
 leading to it being null.
 */

