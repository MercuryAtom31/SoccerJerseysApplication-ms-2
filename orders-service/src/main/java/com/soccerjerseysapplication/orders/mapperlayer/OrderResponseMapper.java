package com.soccerjerseysapplication.orders.mapperlayer;

import com.soccerjerseysapplication.orders.dataaccesslayer.Order;
import com.soccerjerseysapplication.orders.domainclientlayer.customers.CustomerResponseModel;
import com.soccerjerseysapplication.orders.domainclientlayer.jerseys.JerseyResponseModel;
import com.soccerjerseysapplication.orders.domainclientlayer.teams.TeamResponseModel;
import com.soccerjerseysapplication.orders.presentationlayer.OrderResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderResponseMapper {

    @Mappings({
            @Mapping(target = "customerName", source = "customerResponseModel.firstName"),
            @Mapping(target = "customerIdentifier", source = "customerResponseModel.customerId"),
            @Mapping(target = "orderIdentifier", source = "order.orderIdentifier.orderId"),
            @Mapping(target = "orderDate", source = "order.orderDate"),
            @Mapping(target = "totalPriceOrder", source = "order.totalPriceOrder"),
            @Mapping(target = "teamIdentifier", source = "teamResponseModel.teamId"),
            @Mapping(target = "jerseyIdentifier", source = "jerseyResponseModel.jerseyId"),
            @Mapping(target = "size", source = "jerseyResponseModel.size"),
            @Mapping(target = "color", source = "jerseyResponseModel.color"),
            @Mapping(target = "styles", source = "jerseyResponseModel.styles"),
            @Mapping(target = "jerseyPrice", source = "jerseyResponseModel.price"),
            @Mapping(target = "teamName", source = "teamResponseModel.name")
    })
    OrderResponseModel orderAndDetailsToOrderResponseModel(Order order,
                                                           CustomerResponseModel customerResponseModel,
                                                           JerseyResponseModel jerseyResponseModel,
                                                           TeamResponseModel teamResponseModel);
    //  List<OrderResponseModel> orderAndDetailsToOrderListToResponseModelList(List<Order> orders, List<CustomerResponseModel> customerResponseModels, List<JerseyResponseModel> jerseyResponseModels, List<TeamResponseModel> teamResponseModels);

    //OrderResponseModel entityToResponseModel(Order foundOrder);

    //OLD CODE
//    //                  [  parameters of the mapper      ]        [ field in the response model]
//    @Mapping(expression = "java(jerseyResponseModel.getSize())", target = "size")
//    @Mapping(expression = "java(jerseyResponseModel.getColor())", target = "color")
//    @Mapping(expression = "java(jerseyResponseModel.getStyles())", target = "styles")
//    @Mapping(expression = "java(jerseyResponseModel.getPrice())", target = "jerseyPrice")
//    @Mapping(expression = "java(jerseyResponseModel.getJerseyId())", target = "jerseyIdentifier")
//
//    @Mapping(expression = "java(teamResponseModel.getName())", target = "teamName")
//    @Mapping(expression = "java(teamResponseModel.getTeamId())", target = "teamIdentifier")
//
//    @Mapping(expression = "java(customerResponseModel.getCustomerId())", target = "customerIdentifier")
//    @Mapping(expression = "java(customerResponseModel.getCustomerName())", target = "customerName")//Newly Added
//
//    @Mapping(expression = "java(order.getOrderIdentifier().getOrderId())", target = "orderIdentifier")
//    @Mapping(expression = "java(order.getOrderDate())", target = "orderDate")//Newly Added
//
//    OrderResponseModel entityToResponseModel(JerseyResponseModel jerseyResponseModel,
//                                             TeamResponseModel teamResponseModel,
//                                             Order order, CustomerResponseModel customerResponseModel);

}
