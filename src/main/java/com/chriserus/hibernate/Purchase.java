package com.chriserus.hibernate;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;

    public Purchase(Client client, Item item) {
        this.client = client;
        this.item = item;
    }
}
