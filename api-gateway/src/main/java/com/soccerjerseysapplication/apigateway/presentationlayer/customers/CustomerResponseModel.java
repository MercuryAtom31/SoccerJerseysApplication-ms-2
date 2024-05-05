package com.soccerjerseysapplication.apigateway.presentationlayer.customers;

import com.soccerjerseysapplication.apigateway.domainclientlayer.customers.PhoneNumber;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CustomerResponseModel extends RepresentationModel<CustomerResponseModel> {

    String customerId;
    String firstName;
    String lastName;
    String emailAddress;
    String streetAddress;
    String city;
    String province;
    String country;
    String postalCode;
    List<PhoneNumber> phoneNumbers;
}
