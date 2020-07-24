package com.chriserus.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Item", schema = "restaurant")
public class ItemEntity {
    private int id;
    private String name;
    private double price;
    private int calories;
    private boolean vegetarian;

    public ItemEntity() {
    }

    public ItemEntity(String name, double price, int calories, boolean vegetarian) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.vegetarian = vegetarian;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "calories", nullable = false)
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Basic
    @Column(name = "vegetarian", nullable = false)
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
        ItemEntity that = (ItemEntity) o;
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
