package com.canvashub.backend.cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartRepository extends CrudRepository<Cart, String> {
}
