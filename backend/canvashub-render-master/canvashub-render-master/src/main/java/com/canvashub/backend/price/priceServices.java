package com.canvashub.backend.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class priceServices {

    @Autowired
    private priceRepository price_repository;

    public List<Price> getAllPrices()
    {
        List<Price>priceRecords = new ArrayList<>();
        price_repository.findAll().forEach(priceRecords::add);
        return priceRecords;
    }

    public Price getPrice(String id)
    {
        Optional<Price> price = price_repository.findById(id);
        if(price.isPresent()) {
            return price.get();
        }
        else
            return null;
    }

    public Price addPrice(Price price)
    {
        return price_repository.save(price);
    }

    public Price updatePrice(Price price)
    {
        return price_repository.save(price);
    }

    public void deletePrice(String id)
    {
        Optional<Price> price = price_repository.findById(id);
        if(price.isPresent()) {
            price_repository.delete(price.get());
        }
    }

}
