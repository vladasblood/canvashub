package com.canvashub.backend.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class priceController {

    @Autowired
    private priceServices price_service;

    @RequestMapping("/prices")
    public List<Price> getAllPrice()
    {
        return price_service.getAllPrices();
    }

    @RequestMapping(value="/prices/{id}")
    public Price getPrice(@PathVariable String id)
    {
        return price_service.getPrice(id);
    }

    @RequestMapping(value="/add-price", method= RequestMethod.POST)
    public Price addPrice(@RequestBody Price price)
    {
        return price_service.addPrice(price);
    }

    @RequestMapping(value="/update-price", method=RequestMethod.PUT)
    public Price updatePrice(@RequestBody Price price)
    {
        return price_service.updatePrice(price);
    }

    @RequestMapping(value="/prices/{id}", method=RequestMethod.DELETE)
    public void deletePrice(@PathVariable String id)
    {
        price_service.deletePrice(id);
    }
    
}
