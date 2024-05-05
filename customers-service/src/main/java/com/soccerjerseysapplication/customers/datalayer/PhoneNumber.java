package com.soccerjerseysapplication.customers.datalayer;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Christine Gerard
 * @created 02/11/2024
 * @project cardealership-ws-2024
 */

@Embeddable
@NoArgsConstructor
@Getter
public class PhoneNumber {

    @Enumerated(EnumType.STRING)
    private PhoneType type;
    private String number;

    public PhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        this.number = number;
    }
}
