package com.canvashub.backend.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface storeRepository extends CrudRepository<Store, String>{
}
