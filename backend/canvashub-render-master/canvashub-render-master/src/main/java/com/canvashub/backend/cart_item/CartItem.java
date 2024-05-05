package com.canvashub.backend.cart_item;

import com.canvashub.backend.cart.Cart;
import com.canvashub.backend.price.Price;
import com.canvashub.backend.product.Product;
import com.canvashub.backend.store.Store;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CART_ITEM")
public class CartItem {
    @Id
    @Column(name = "CART_ITEM_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")
    private Cart cart_Id;

    @ManyToOne
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    private Store store_Id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product_Id;

    @Column(name = "QUANTITY")
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "PRICE_ID", referencedColumnName = "PRICE_ID")
    private Price price;
}
