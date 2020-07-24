package com.chriserus.hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Client", schema = "restaurant")
public class ClientEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Basic
    @Column(name = "surname", nullable = false, length = 150)
    private String surname;

    @Basic
    @Column(name = "vegetarian", nullable = false)
    private boolean vegetarian;

    @Basic
    @Column(name = "tableNo", columnDefinition = "int default 0")
    private Integer tableNo;

    @Basic
    @Column(name = "finish", nullable = true)
    private Timestamp finish;

    @Basic
    @Column(name = "orderNo", nullable = true)
    private Integer orderNo;

    @Basic
    @Column(name = "orderTotal", nullable = true, precision = 0)
    private Double orderTotal;

    @Basic
    @Column(name = "caloriesTotal", nullable = true)
    private Integer caloriesTotal;


    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Integer getTableNo() {
        return tableNo;
    }
    public void setTableNo(Integer tableNo) {
        this.tableNo = tableNo;
    }

    public Timestamp getFinish() {
        return finish;
    }
    public void setFinish(Timestamp finish) {
        this.finish = finish;
    }

    public Integer getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
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

    //ClientEntity constructor


    public ClientEntity() {
    }

    public ClientEntity(String name, String surname, boolean vegetarian) {
        this.name = name;
        this.surname = surname;
        this.vegetarian = vegetarian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return id == that.id &&
                vegetarian == that.vegetarian &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(tableNo, that.tableNo) &&
                Objects.equals(finish, that.finish) &&
                Objects.equals(orderNo, that.orderNo) &&
                Objects.equals(orderTotal, that.orderTotal) &&
                Objects.equals(caloriesTotal, that.caloriesTotal);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, surname, vegetarian, tableNo, finish, orderNo, orderTotal, caloriesTotal);
    }
}
