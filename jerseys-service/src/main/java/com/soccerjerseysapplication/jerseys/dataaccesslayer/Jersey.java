package com.soccerjerseysapplication.jerseys.dataaccesslayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jerseys")
@NoArgsConstructor
@Data
public class Jersey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private JerseyIdentifier jerseyIdentifier;
    private String size;
    private String color;
    private String styles; // Short sleeves or long sleeves.

    private Integer quantity; // Total available quantity of jerseys.
    private Double price;

    public Jersey(@NotNull JerseyIdentifier jerseyIdentifier,
                  @NotNull String size, @NotNull String color, @NotNull String styles,
                  @NotNull Integer quantity, @NotNull Double price) {
        this.jerseyIdentifier = jerseyIdentifier;
        this.size = size;
        this.color = color;
        this.styles = styles;
        this.quantity = quantity;
        this.price = price;
    }


}
