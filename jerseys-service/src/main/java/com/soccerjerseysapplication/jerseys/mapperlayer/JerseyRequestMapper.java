package com.soccerjerseysapplication.jerseys.mapperlayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.dataaccesslayer.JerseyIdentifier;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyRequestModel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JerseyRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jerseyIdentifier", source = "jerseyIdentifier")
    Jersey requestModelToEntity(JerseyRequestModel requestModel, JerseyIdentifier jerseyIdentifier);
}
