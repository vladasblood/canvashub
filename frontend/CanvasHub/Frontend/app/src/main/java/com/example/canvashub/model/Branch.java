package com.example.canvashub.model;


import java.util.Objects;

public class Branch {

    private String id;
    private Store store_id;
    private Address address_id;

    public Branch() {
    }

    public Branch(String id, Store store_id, Address address_id) {
        this.id = id;
        this.store_id = store_id;
        this.address_id = address_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Store getStore_id() {
        return store_id;
    }

    public void setStore_id(Store store_id) {
        this.store_id = store_id;
    }

    public Address getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Address address_id) {
        this.address_id = address_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branch)) return false;
        Branch branch = (Branch) o;
        return Objects.equals(id, branch.id) && Objects.equals(store_id, branch.store_id) && Objects.equals(address_id, branch.address_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store_id, address_id);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id='" + id + '\'' +
                ", store_id=" + store_id +
                ", address_id=" + address_id +
                '}';
    }
}
