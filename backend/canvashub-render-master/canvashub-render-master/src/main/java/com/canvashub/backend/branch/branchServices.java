package com.canvashub.backend.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class branchServices {

    @Autowired
    private branchRepository branch_repository;

    public List<Branch> getAllBranches()
    {
        List<Branch>branchRecords = new ArrayList<>();
        branch_repository.findAll().forEach(branchRecords::add);
        return branchRecords;
    }

    public Branch getBranch(String id)
    {
        Optional<Branch> branch = branch_repository.findById(id);
        if(branch.isPresent()) {
            return branch.get();
        }
        else
            return null;
    }

    public Branch addBranch(Branch branch)
    {
        return branch_repository.save(branch);
    }

    public Branch updateBranch(Branch branch)
    {
        return branch_repository.save(branch);
    }

    public void deleteBranch(String id)
    {
        Optional<Branch> branch = branch_repository.findById(id);
        if(branch.isPresent()) {
            branch_repository.delete(branch.get());
        }
    }
    
}
