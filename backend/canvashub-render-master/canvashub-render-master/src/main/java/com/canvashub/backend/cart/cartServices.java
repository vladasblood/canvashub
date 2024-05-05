package com.canvashub.backend.cart;

import com.canvashub.backend.cart.Cart;
import com.canvashub.backend.cart.cartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class cartServices {

    @Autowired
    private cartRepository cart_repository;

    public List<Cart> getAllCarts()
    {
        List<Cart>cartRecords = new ArrayList<>();
        cart_repository.findAll().forEach(cartRecords::add);
        return cartRecords;
    }

    public Cart getCart(String id)
    {
        Optional<Cart> cart = cart_repository.findById(id);
        if(cart.isPresent()) {
            return cart.get();
        }
        else
            return null;
    }

    public Cart addCart(Cart cart)
    {
        return cart_repository.save(cart);
    }

    public Cart updateCart(Cart cart)
    {
        return cart_repository.save(cart);
    }

    public void deleteCart(String id)
    {
        Optional<Cart> cart = cart_repository.findById(id);
        if(cart.isPresent()) {
            cart_repository.delete(cart.get());
        }
    }
    
}
