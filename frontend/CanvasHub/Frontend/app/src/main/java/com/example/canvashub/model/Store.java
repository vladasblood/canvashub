package com.example.canvashub.model;

import java.util.Objects;

public class Store {


    private String id;
    private String store_name;

    private String store_image_url;

    public Store() {
    }

    public Store(String id, String store_name, String store_image_url) {
        this.id = id;
        this.store_name = store_name;
        this.store_image_url = store_image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_image_url() {
        return store_image_url;
    }

    public void setStore_image_url(String store_image_url) {
        this.store_image_url = store_image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(id, store.id) && Objects.equals(store_name, store.store_name) && Objects.equals(store_image_url, store.store_image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, store_name, store_image_url);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id='" + id + '\'' +
                ", store_name='" + store_name + '\'' +
                ", store_image_url='" + store_image_url + '\'' +
                '}';
    }
}
