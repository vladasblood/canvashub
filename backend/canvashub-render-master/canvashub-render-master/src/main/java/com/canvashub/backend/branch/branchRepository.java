package com.canvashub.backend.branch;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface branchRepository extends CrudRepository<Branch, String> {
}
