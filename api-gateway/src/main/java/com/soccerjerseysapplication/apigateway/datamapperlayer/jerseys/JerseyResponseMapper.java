package com.soccerjerseysapplication.apigateway.datamapperlayer.jerseys;

import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyController;
import com.soccerjerseysapplication.apigateway.presentationlayer.jerseys.JerseyResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface JerseyResponseMapper {

    JerseyResponseModel responseModelToResponseModel(JerseyResponseModel jerseyResponseModel);

    List<JerseyResponseModel> responseModelListToResponseModelList(List<JerseyResponseModel> jerseyResponseModelList);

    @AfterMapping
    default void addLinks(@MappingTarget JerseyResponseModel jerseyResponseModel) {
        // jerseyLink
        Link selfLink = linkTo(methodOn(JerseyController.class)
                .getJerseyByJerseyId(jerseyResponseModel.getJerseyId()))
                .withSelfRel();
        jerseyResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(JerseyController.class)
                .getAllJerseys())
                .withRel("all jerseys");
        jerseyResponseModel.add(allLink);
    }
}
