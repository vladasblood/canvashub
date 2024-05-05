package com.canvashub.backend.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Data;

@Entity
@Data
@Table(name = "STORE")
public class Store {
    @Id
    @Column(name = "STORE_ID")
    private String id;

    @Column(name = "STORE_NAME")
    private String store_name;

    @Column(name = "STORE_IMAGE_URL")
    private String store_image_url;
}
