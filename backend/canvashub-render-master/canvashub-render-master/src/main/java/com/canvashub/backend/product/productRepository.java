package com.canvashub.backend.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends CrudRepository<Product, String> {
}
