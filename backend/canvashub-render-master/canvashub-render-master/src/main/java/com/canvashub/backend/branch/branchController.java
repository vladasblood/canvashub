package com.canvashub.backend.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class branchController {

    @Autowired
    private branchServices branch_service;

    @RequestMapping("/branches")
    public List<Branch> getAllBranches()
    {
        return branch_service.getAllBranches();
    }

    @RequestMapping(value="/branches/{id}")
    public Branch getBranch(@PathVariable String id)
    {
        return branch_service.getBranch(id);
    }

    @RequestMapping(value="/add-branch", method= RequestMethod.POST)
    public Branch addBranch(@RequestBody Branch branch)
    {
        return branch_service.addBranch(branch);
    }

    @RequestMapping(value="/update-branch", method=RequestMethod.PUT)
    public Branch updateBranch(@RequestBody Branch branch)
    {
        return branch_service.updateBranch(branch);
    }

    @RequestMapping(value="/branches/{id}", method=RequestMethod.DELETE)
    public void deleteBranch(@PathVariable String id)
    {
        branch_service.deleteBranch(id);
    }
}
