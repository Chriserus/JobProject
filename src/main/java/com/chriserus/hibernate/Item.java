package com.chriserus.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Item {

    @Id
    private int id;
    private String name;
    private double price;
    private int calories;
    private boolean vegetarian;

    public Item() {
    }

    public Item(String name, double price, int calories, boolean vegetarian) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.vegetarian = vegetarian;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                calories == that.calories &&
                vegetarian == that.vegetarian &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, price, calories, vegetarian);
    }
}
