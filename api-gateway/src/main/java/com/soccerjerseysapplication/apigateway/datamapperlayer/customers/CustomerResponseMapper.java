package com.soccerjerseysapplication.apigateway.datamapperlayer.customers;


import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerController;
import com.soccerjerseysapplication.apigateway.presentationlayer.customers.CustomerResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CustomerResponseMapper {

    CustomerResponseModel responseModelToResponseModel(CustomerResponseModel customerResponseModel);

    List<CustomerResponseModel> responseModelListToResponseModelList(List<CustomerResponseModel> customerResponseModelList);

    @AfterMapping
    default void addLinks(@MappingTarget CustomerResponseModel customerResponseModel) {
        // customerLink
        Link selfLink = linkTo(methodOn(CustomerController.class)
                .getCustomerByCustomerId(customerResponseModel.getCustomerId()))
                .withSelfRel();
        customerResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(CustomerController.class)
                .getAllCustomers())
                .withRel("all customers");
        customerResponseModel.add(allLink);
    }
}
