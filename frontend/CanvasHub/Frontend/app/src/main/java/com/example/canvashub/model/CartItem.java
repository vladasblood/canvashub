package com.example.canvashub.model;

import java.util.Objects;

public class CartItem {

    private String id;

    private Cart cart_Id;

    private Store store_Id;

    private Product product_Id;

    private String quantity;

    private Price price;

    public CartItem() {
    }

    public CartItem(String id, Cart cart_Id, Store store_Id, Product product_Id, String quantity, Price price) {
        this.id = id;
        this.cart_Id = cart_Id;
        this.store_Id = store_Id;
        this.product_Id = product_Id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cart getCart_Id() {
        return cart_Id;
    }

    public void setCart_Id(Cart cart_Id) {
        this.cart_Id = cart_Id;
    }

    public Store getStore_Id() {
        return store_Id;
    }

    public void setStore_Id(Store store_Id) {
        this.store_Id = store_Id;
    }

    public Product getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(Product product_Id) {
        this.product_Id = product_Id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(cart_Id, cartItem.cart_Id) && Objects.equals(store_Id, cartItem.store_Id) && Objects.equals(product_Id, cartItem.product_Id) && Objects.equals(quantity, cartItem.quantity) && Objects.equals(price, cartItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart_Id, store_Id, product_Id, quantity, price);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", cart_Id=" + cart_Id +
                ", store_Id=" + store_Id +
                ", product_Id=" + product_Id +
                ", quantity='" + quantity + '\'' +
                ", price=" + price +
                '}';
    }
}
