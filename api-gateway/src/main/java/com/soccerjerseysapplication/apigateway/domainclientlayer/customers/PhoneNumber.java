package com.soccerjerseysapplication.apigateway.domainclientlayer.customers;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Christine Gerard
 * @created 02/11/2024
 * @project cardealership-ws-2024
 */

@NoArgsConstructor
@Getter
public class PhoneNumber {

    private PhoneType type;
    private String number;

    public PhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        this.number = number;
    }
}
