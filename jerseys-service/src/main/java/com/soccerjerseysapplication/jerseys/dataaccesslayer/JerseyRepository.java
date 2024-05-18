package com.soccerjerseysapplication.jerseys.dataaccesslayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JerseyRepository extends JpaRepository<Jersey, Long> {

    // Find a jerseys by its identifier


    // Find jerseys by color
    List<Jersey> findJerseysByColor(String color);

    // Find jerseys by size and style
    List<Jersey> findJerseysBySizeAndStyles(String size, String styles);

    // Find jerseys with stock amount greater than a given value
    List<Jersey> findJerseysByQuantityGreaterThan(Integer quantity);

    Jersey findByJerseyIdentifier_JerseyId(String jerseyId);
}
