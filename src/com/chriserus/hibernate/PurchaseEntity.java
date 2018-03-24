package com.chriserus.hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Purchase", schema = "restaurant")
public class PurchaseEntity {
    private int id;
    private ClientEntity clientByClientid;
    private ItemEntity itemByMenuid;

    public PurchaseEntity(ClientEntity client, ItemEntity item) {
        clientByClientid = client;
        itemByMenuid = item;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "clientid", referencedColumnName = "id", nullable = false)
    public ClientEntity getClientByClientid() {
        return clientByClientid;
    }

    public void setClientByClientid(ClientEntity clientByClientid) {
        this.clientByClientid = clientByClientid;
    }

    @ManyToOne
    @JoinColumn(name = "menuid", referencedColumnName = "id", nullable = false)
    public ItemEntity getItemByMenuid() {
        return itemByMenuid;
    }

    public void setItemByMenuid(ItemEntity itemByMenuid) {
        this.itemByMenuid = itemByMenuid;
    }
}
