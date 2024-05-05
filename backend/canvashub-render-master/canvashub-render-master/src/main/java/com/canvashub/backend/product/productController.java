package com.canvashub.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class productController {

    @Autowired
    private productServices product_service;

    @RequestMapping("/products")
    public List<Product> getAllProduct()
    {
        return product_service.getAllProducts();
    }

    @RequestMapping(value="/products/{id}")
    public Product getProduct(@PathVariable String id)
    {
        return product_service.getProduct(id);
    }

    @RequestMapping(value="/add-product", method= RequestMethod.POST)
    public Product addProduct(@RequestBody Product product)
    {
        return product_service.addProduct(product);
    }

    @RequestMapping(value="/update-product", method=RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product)
    {
        return product_service.updateProduct(product);
    }

    @RequestMapping(value="/products/{id}", method=RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id)
    {
        product_service.deleteProduct(id);
    }

}
