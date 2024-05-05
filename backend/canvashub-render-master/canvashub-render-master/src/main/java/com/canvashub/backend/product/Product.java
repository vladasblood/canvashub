package com.canvashub.backend.product;

import com.canvashub.backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

    @Column(name = "PRODUCT_NAME")
    private String product_name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    private Category category_Id;

    @Column(name = "PRODUCT_IMAGE_URL")
    private String product_image_url;
}
