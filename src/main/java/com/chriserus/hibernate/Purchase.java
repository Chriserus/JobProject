package com.chriserus.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Purchase {
    @Id
    private int id;

    @ManyToOne
//    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne
//    @JoinColumn(name = "item")
    private Item item;

    public Purchase(Client client, Item item) {
        this.client = client;
        this.item = item;
    }

    public Purchase() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase that = (Purchase) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
