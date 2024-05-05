package com.soccerjerseysapplication.customers.presentationlayer;

import com.soccerjerseysapplication.customers.datalayer.PhoneNumber;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CustomerResponseModel {

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
