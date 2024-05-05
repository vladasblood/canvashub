package com.canvashub.backend.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class categoryController {

    @Autowired
    private categoryServices category_service;

    @RequestMapping("/categories")
    public List<Category> getAllCategory()
    {
        return category_service.getAllCategories();
    }

    @RequestMapping(value="/categories/{id}")
    public Category getCategory(@PathVariable String id)
    {
        return category_service.getCategory(id);
    }

    @RequestMapping(value="/add-category", method= RequestMethod.POST)
    public Category addCategory(@RequestBody Category category)
    {
        return category_service.addCategory(category);
    }

    @RequestMapping(value="/update-category", method=RequestMethod.PUT)
    public Category updateCategory(@RequestBody Category category)
    {
        return category_service.updateCategory(category);
    }

    @RequestMapping(value="/categories/{id}", method=RequestMethod.DELETE)
    public void deleteCategory(@PathVariable String id)
    {
        category_service.deleteCategory(id);
    }
    
}
