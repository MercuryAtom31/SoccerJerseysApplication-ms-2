package com.soccerjerseysapplication.apigateway.datamapperlayer.orders;

import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderController;
import com.soccerjerseysapplication.apigateway.presentationlayer.orders.OrderResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OrderResponseMapper {

    OrderResponseModel responseModelToResponseModel(OrderResponseModel orderResponseModel);

    List<OrderResponseModel> responseModelListToResponseModelList(List<OrderResponseModel> orderResponseModelList);

    @AfterMapping
    default void addLinks(@MappingTarget OrderResponseModel orderResponseModel) {
        // orderLink
        String orderId = orderResponseModel.getOrderIdentifier();
        String customerId  = orderResponseModel.getCustomerIdentifier();
        Link selfLink = linkTo(methodOn(OrderController.class)
                .getCustomerOrderByOrderId(customerId, orderId))
                .withSelfRel();
        orderResponseModel.add(selfLink);

        Link allLink = linkTo(methodOn(OrderController.class)
                .getAllOrdersForCustomer(customerId))
                .withRel("all orders");
        orderResponseModel.add(allLink);
    }
}
