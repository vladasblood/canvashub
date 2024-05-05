package com.canvashub.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class productServices {

    @Autowired
    private productRepository product_repository;

    public List<Product> getAllProducts()
    {
        List<Product>productRecords = new ArrayList<>();
        product_repository.findAll().forEach(productRecords::add);
        return productRecords;
    }

    public Product getProduct(String id)
    {
        Optional<Product> product = product_repository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }
        else
            return null;
    }

    public Product addProduct(Product product)
    {
        return product_repository.save(product);
    }

    public Product updateProduct(Product product)
    {
        return product_repository.save(product);
    }

    public void deleteProduct(String id)
    {
        Optional<Product> product = product_repository.findById(id);
        if(product.isPresent()) {
            product_repository.delete(product.get());
        }
    }
    
}
