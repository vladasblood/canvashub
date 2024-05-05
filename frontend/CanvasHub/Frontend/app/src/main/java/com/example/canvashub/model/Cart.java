package com.example.canvashub.model;

import java.util.Objects;

public class Cart {
    private String id;
    private Customer customer_id;

    public Cart() {
    }

    public Cart(String id, Customer customer_id) {
        this.id = id;
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Customer customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(customer_id, cart.customer_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer_id);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
