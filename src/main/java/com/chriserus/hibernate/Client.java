package com.chriserus.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Client {

    @Id
    private int id;
    private String firstName;
    private String secondName;
    private boolean vegetarian;
    private Integer tableNumber;
    private Timestamp finished;
    private Integer orderNumber;
    private Double orderTotal;
    private Integer caloriesTotal;


    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Timestamp getFinished() {
        return finished;
    }
    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Integer getCaloriesTotal() {
        return caloriesTotal;
    }
    public void setCaloriesTotal(Integer caloriesTotal) {
        this.caloriesTotal = caloriesTotal;
    }

    //Client constructor


    public Client() {
    }

    public Client(String firstName, String secondName, boolean vegetarian) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.vegetarian = vegetarian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client that = (Client) o;
        return id == that.id &&
                vegetarian == that.vegetarian &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(secondName, that.secondName) &&
                Objects.equals(tableNumber, that.tableNumber) &&
                Objects.equals(finished, that.finished) &&
                Objects.equals(orderNumber, that.orderNumber) &&
                Objects.equals(orderTotal, that.orderTotal) &&
                Objects.equals(caloriesTotal, that.caloriesTotal);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, secondName, vegetarian, tableNumber, finished, orderNumber, orderTotal, caloriesTotal);
    }
}
