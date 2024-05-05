package com.canvashub.backend.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class customerController {

    @Autowired
    private customerServices customer_service;

    @RequestMapping("/customers")
    public List<Customer> getAllCustomer()
    {
        return customer_service.getAllCustomers();
    }

    @RequestMapping(value="/customers/{id}")
    public Customer getCustomer(@PathVariable String id)
    {
        return customer_service.getCustomer(id);
    }

    @RequestMapping(value="/add-customer", method= RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer)
    {
        return customer_service.addCustomer(customer);
    }

    @RequestMapping(value="/update-customer", method=RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer)
    {
        return customer_service.updateCustomer(customer);
    }

    @RequestMapping(value="/customers/{id}", method=RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable String id)
    {
        customer_service.deleteCustomer(id);
    }
    
}
