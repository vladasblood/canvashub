package com.canvashub.backend.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepository extends CrudRepository<Category, String> {
}
