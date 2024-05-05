package com.canvashub.backend.cart_item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cartItemRepository extends CrudRepository<CartItem, String> {
}
