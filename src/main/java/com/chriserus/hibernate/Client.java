package com.chriserus.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private boolean vegetarian;
    private Integer tableNumber;
    private Timestamp finished;
    private Integer orderNumber;
    private Double orderTotal;
    private Integer caloriesTotal;

    public Client(String firstName, String secondName, boolean vegetarian) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.vegetarian = vegetarian;
    }
}
