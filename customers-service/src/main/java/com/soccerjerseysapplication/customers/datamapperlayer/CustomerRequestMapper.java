package com.soccerjerseysapplication.customers.datamapperlayer;

import com.soccerjerseysapplication.customers.datalayer.Address;
import com.soccerjerseysapplication.customers.datalayer.Customer;
import com.soccerjerseysapplication.customers.datalayer.CustomerIdentifier;
import com.soccerjerseysapplication.customers.presentationlayer.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    Customer requestModelToEntity(CustomerRequestModel customerRequestModel, CustomerIdentifier customerIdentifier,
                                  Address address);
}
