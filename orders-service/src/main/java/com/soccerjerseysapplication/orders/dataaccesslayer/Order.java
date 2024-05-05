package com.soccerjerseysapplication.orders.dataaccesslayer;


import com.soccerjerseysapplication.orders.dataaccesslayer.OrderIdentifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //private identifier
    @Embedded
    private OrderIdentifier orderIdentifier;
    @Embedded
    private CustomerIdentifier customerIdentifier;
    @Embedded
    private JerseyIdentifier jerseyIdentifier;
    @Embedded
    private TeamIdentifier teamIdentifier;
    private String orderDate;//
    private double totalPriceOrder;//
}
