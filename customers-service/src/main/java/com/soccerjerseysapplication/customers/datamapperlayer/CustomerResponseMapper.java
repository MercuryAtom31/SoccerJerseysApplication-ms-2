package com.soccerjerseysapplication.customers.datamapperlayer;

import com.soccerjerseysapplication.customers.datalayer.Customer;
import com.soccerjerseysapplication.customers.presentationlayer.CustomerResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {

    @Mapping(expression = "java(customer.getCustomerIdentifier().getCustomerId())", target = "customerId")
    @Mapping(expression = "java(customer.getAddress().getStreetAddress())", target = "streetAddress")
    @Mapping(expression = "java(customer.getAddress().getCity())", target = "city")
    @Mapping(expression = "java(customer.getAddress().getProvince())", target = "province")
    @Mapping(expression = "java(customer.getAddress().getCountry())", target = "country")
    @Mapping(expression = "java(customer.getAddress().getPostalCode())", target = "postalCode")
    CustomerResponseModel entityToResponseModel(Customer customer);

    List<CustomerResponseModel> entityListToResponseModelList(List<Customer> customers);

//    @AfterMapping
//    default void addLinks(@MappingTarget CustomerResponseModel model, Customer customer) {
//        //self link
//        Link selfLink = linkTo(methodOn(CustomerController.class)
//                .getCustomerByCustomerId(model.getCustomerId()))
//                .withSelfRel();
//        model.add(selfLink);
//
//        // all customers link
//        Link customersLink = linkTo(methodOn(CustomerController.class)
//                .getCustomers())
//                .withRel("customers");
//        model.add(customersLink);
//    }
}
