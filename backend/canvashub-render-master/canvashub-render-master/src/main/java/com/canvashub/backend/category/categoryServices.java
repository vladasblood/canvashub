package com.canvashub.backend.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class categoryServices {

    @Autowired

    private categoryRepository category_repository;

    public List<Category> getAllCategories()
    {
        List<Category>categoryRecords = new ArrayList<>();
        category_repository.findAll().forEach(categoryRecords::add);
        return categoryRecords;
    }

    public Category getCategory(String id)
    {
        Optional<Category> category = category_repository.findById(id);
        if(category.isPresent()) {
            return category.get();
        }
        else
            return null;
    }

    public Category addCategory(Category category)
    {
        return category_repository.save(category);
    }

    public Category updateCategory(Category category)
    {
        return category_repository.save(category);
    }

    public void deleteCategory(String id)
    {
        Optional<Category> category = category_repository.findById(id);
        if(category.isPresent()) {
            category_repository.delete(category.get());
        }
    }

}
