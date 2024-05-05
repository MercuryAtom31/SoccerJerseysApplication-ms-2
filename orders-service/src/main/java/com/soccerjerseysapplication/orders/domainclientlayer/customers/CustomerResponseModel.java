package com.soccerjerseysapplication.orders.domainclientlayer.customers;

import com.soccerjerseysapplication.orders.domainclientlayer.customers.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
