package com.example.canvashub.model;


import java.util.Objects;

public class Category {

    private String id;
    private String category_name;
    private String category_image_url;

    public Category() {
    }

    public Category(String id, String category_name, String category_image_url) {
        this.id = id;
        this.category_name = category_name;
        this.category_image_url = category_image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image_url() {
        return category_image_url;
    }

    public void setCategory_image_url(String category_image_url) {
        this.category_image_url = category_image_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(category_name, category.category_name) && Objects.equals(category_image_url, category.category_image_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category_name, category_image_url);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", category_image_url='" + category_image_url + '\'' +
                '}';
    }
}
