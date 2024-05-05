package com.canvashub.backend.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customerRepository extends CrudRepository<Customer, String> {
}
