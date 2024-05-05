package com.canvashub.backend.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class cartController {

    @Autowired
    private cartServices cart_service;

    @RequestMapping("/carts")
    public List<Cart> getAllCart()
    {
        return cart_service.getAllCarts();
    }

    @RequestMapping(value="/carts/{id}")
    public Cart getCart(@PathVariable String id)
    {
        return cart_service.getCart(id);
    }

    @RequestMapping(value="/add-cart", method= RequestMethod.POST)
    public Cart addCart(@RequestBody Cart cart)
    {
        return cart_service.addCart(cart);
    }

    @RequestMapping(value="/update-cart", method=RequestMethod.PUT)
    public Cart updateCart(@RequestBody Cart cart)
    {
        return cart_service.updateCart(cart);
    }

    @RequestMapping(value="/carts/{id}", method=RequestMethod.DELETE)
    public void deleteCart(@PathVariable String id)
    {
        cart_service.deleteCart(id);
    }
    
}
