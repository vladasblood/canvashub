package com.canvashub.backend.category;

import com.canvashub.backend.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CATEGORY")
public class Category {
    @Id
    @Column(name = "CATEGORY_ID")
    private String id;

    @Column(name = "CATEGORY_NAME")
    private String category_name;

    @Column(name = "CATEGORY_IMAGE_URL")
    private String category_image_url;
}
