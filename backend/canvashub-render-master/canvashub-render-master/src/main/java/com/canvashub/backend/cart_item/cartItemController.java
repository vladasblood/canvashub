package com.canvashub.backend.cart_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class cartItemController {

    @Autowired
    private cartItemServices cartItem_service;

    @RequestMapping("/cartItems")
    public List<CartItem> getAllCartItem()
    {
        return cartItem_service.getAllCartItems();
    }

    @RequestMapping(value="/cartItems/{id}")
    public CartItem getCartItem(@PathVariable String id)
    {
        return cartItem_service.getCartItem(id);
    }

    @RequestMapping(value="/add-cartItem", method= RequestMethod.POST)
    public CartItem addCartItem(@RequestBody CartItem cartItem)
    {
        return cartItem_service.addCartItem(cartItem);
    }

    @RequestMapping(value="/update-cartItem", method=RequestMethod.PUT)
    public CartItem updateCartItem(@RequestBody CartItem cartItem)
    {
        return cartItem_service.updateCartItem(cartItem);
    }

    @RequestMapping(value="/cartItems/{id}", method=RequestMethod.DELETE)
    public void deleteCartItem(@PathVariable String id)
    {
        cartItem_service.deleteCartItem(id);
    }
    
}
