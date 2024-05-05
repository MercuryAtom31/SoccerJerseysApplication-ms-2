package com.soccerjerseysapplication.apigateway.presentationlayer.jerseys;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JerseyResponseModel extends RepresentationModel<JerseyResponseModel> {

    private String jerseyId;
    private String size;
    private String color;
    private String styles;
    private Integer stockAmount;
    private Double price;
    private String customerName;
    private String orderDate;
}
