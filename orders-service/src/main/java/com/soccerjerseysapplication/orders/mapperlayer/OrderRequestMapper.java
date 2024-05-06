package com.soccerjerseysapplication.orders.mapperlayer;

import com.soccerjerseysapplication.orders.dataaccesslayer.JerseyIdentifier;
import com.soccerjerseysapplication.orders.dataaccesslayer.Order;
import com.soccerjerseysapplication.orders.dataaccesslayer.OrderIdentifier;
import com.soccerjerseysapplication.orders.dataaccesslayer.TeamIdentifier;
import com.soccerjerseysapplication.orders.presentationlayer.OrderRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "customerIdentifier", ignore = true)
    @Mapping(target = "totalPriceOrder", source = "orderRequestModel.jerseyIdentifier")
    @Mapping(target = "teamIdentifier", source = "orderRequestModel.teamIdentifier")
    @Mapping(target = "quantity", source = "orderRequestModel.quantity")
    Order requestModelToEntity(OrderRequestModel orderRequestModel, OrderIdentifier orderIdentifier);

    /**
     * When we will pass String values from OrderRequestModel to requestModelToEntity method,
     * MapStruct will use these custom mapping methods to convert String values
     * to JerseyIdentifier and TeamIdentifier objects.
     */
    default JerseyIdentifier mapToJerseyIdentifier(String value) {
        return new JerseyIdentifier(value);
    }

    default TeamIdentifier mapToTeamIdentifier(String value) {
        return new TeamIdentifier(value);
    }
    
    //OLD CODIGO.
//    @Mapping(target = "customerName", source = "customerName")
//    @Mapping(target = "productName", source = "productName")
//    @Mapping(target = "quantity", source = "quantity")

//    @Mapping(target = "orderDate", expression = "java(java.time.LocalDate.now())")
//    Order modelToEntity(OrderRequestModel requestModel);
}
