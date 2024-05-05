package com.canvashub.backend.cart_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class cartItemServices {

    @Autowired
    private cartItemRepository cartItem_repository;

    public List<CartItem> getAllCartItems()
    {
        List<CartItem>cartItemRecords = new ArrayList<>();
        cartItem_repository.findAll().forEach(cartItemRecords::add);
        return cartItemRecords;
    }

    public CartItem getCartItem(String id)
    {
        Optional<CartItem> cartItem = cartItem_repository.findById(id);
        if(cartItem.isPresent()) {
            return cartItem.get();
        }
        else
            return null;
    }

    public CartItem addCartItem(CartItem cartItem)
    {
        return cartItem_repository.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem)
    {
        return cartItem_repository.save(cartItem);
    }

    public void deleteCartItem(String id)
    {
        Optional<CartItem> cartItem = cartItem_repository.findById(id);
        if(cartItem.isPresent()) {
            cartItem_repository.delete(cartItem.get());
        }
    }
    
}
