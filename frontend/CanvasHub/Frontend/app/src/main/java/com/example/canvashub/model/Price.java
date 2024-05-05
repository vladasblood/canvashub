package com.example.canvashub.model;

import java.util.Objects;

public class Price {
    private String id;

    private Product product_Id;

    private Float cost;

    private Store store_Id;

    public Price() {
    }

    public Price(String id, Product product_Id, Float cost, Store store_Id) {
        this.id = id;
        this.product_Id = product_Id;
        this.cost = cost;
        this.store_Id = store_Id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(Product product_Id) {
        this.product_Id = product_Id;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Store getStore_Id() {
        return store_Id;
    }

    public void setStore_Id(Store store_Id) {
        this.store_Id = store_Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Objects.equals(id, price.id) && Objects.equals(product_Id, price.product_Id) && Objects.equals(cost, price.cost) && Objects.equals(store_Id, price.store_Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_Id, cost, store_Id);
    }

    @Override
    public String toString() {
        return "Price{" +
                "id='" + id + '\'' +
                ", product_Id=" + product_Id +
                ", cost=" + cost +
                ", store_Id=" + store_Id +
                '}';
    }
}
