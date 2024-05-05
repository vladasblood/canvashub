package com.canvashub.backend.price;

import com.canvashub.backend.store.Store;
import com.canvashub.backend.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PRICE")
public class Price {
    @Id
    @Column(name = "PRICE_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product_Id;

    @Column(name = "COST")
    private Float cost;

    @ManyToOne
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    private Store store_Id;
}
