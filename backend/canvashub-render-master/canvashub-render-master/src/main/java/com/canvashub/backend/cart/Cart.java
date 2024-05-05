package com.canvashub.backend.cart;

import com.canvashub.backend.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CART")
public class Cart {
    @Id
    @Column(name = "CART_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID")
    private Customer customer_id;
}
