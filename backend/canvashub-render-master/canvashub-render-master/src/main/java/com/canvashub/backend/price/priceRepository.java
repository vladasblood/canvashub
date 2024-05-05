package com.canvashub.backend.price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface priceRepository extends CrudRepository<Price, String> {
}
