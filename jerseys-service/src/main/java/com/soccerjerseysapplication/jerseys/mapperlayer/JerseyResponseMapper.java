package com.soccerjerseysapplication.jerseys.mapperlayer;

import com.soccerjerseysapplication.jerseys.dataaccesslayer.Jersey;
import com.soccerjerseysapplication.jerseys.presentationlayer.JerseyResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JerseyResponseMapper {
    @Mapping(target = "jerseyId", expression = "java(jersey.getJerseyIdentifier().getJerseyId())")
    @Mapping(target = "size", expression = "java(jersey.getSize())")
    @Mapping(target = "color", expression = "java(jersey.getColor())")
    @Mapping(target = "styles", expression = "java(jersey.getStyles())")
    @Mapping(target = "quantity", expression = "java(jersey.getQuantity())")
    @Mapping(target = "price", expression = "java(jersey.getPrice())")
    JerseyResponseModel entityToResponseModel(Jersey jersey);

    List<JerseyResponseModel> entityListToResponseModelList(List<Jersey> jerseys);
}
