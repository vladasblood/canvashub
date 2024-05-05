package com.canvashub.backend.address;

import com.canvashub.backend.cart.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface addressRepository extends CrudRepository<Address, String> {
}
