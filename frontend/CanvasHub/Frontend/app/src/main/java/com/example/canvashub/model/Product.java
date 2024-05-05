package com.example.canvashub.model;

import java.util.Objects;

public class Product {

    private String id;

    private String product_name;

    private String description;

    private Category category_Id;

    private String product_image_url;

    public Product() {
    }

    public Product(String id, String product_name, String description, Category category_Id, String product_image_url) {
        this.id = id;
        this.product_name = product_name;
        this.description = description;
        this.category_Id = category_Id;
        this.product_image_url = product_image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory_Id() {
        return category_Id;
    }

    public void setCategory_Id(Category category_Id) {
        this.category_Id = category_Id;
    }

    public String getProduct_image_url() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(product_name, product.product_name) && Objects.equals(description, product.description) && Objects.equals(category_Id, product.category_Id) && Objects.equals(product_image_url, product.product_image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product_name, description, category_Id, product_image_url);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", category_Id=" + category_Id +
                ", product_image_url='" + product_image_url + '\'' +
                '}';
    }
}
