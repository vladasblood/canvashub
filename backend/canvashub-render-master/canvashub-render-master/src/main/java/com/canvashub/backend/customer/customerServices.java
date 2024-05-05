package com.canvashub.backend.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class customerServices {

    @Autowired
    private customerRepository customer_repository;

    public List<Customer> getAllCustomers()
    {
        List<Customer>customerRecords = new ArrayList<>();
        customer_repository.findAll().forEach(customerRecords::add);
        return customerRecords;
    }

    public Customer getCustomer(String id)
    {
        Optional<Customer> customer = customer_repository.findById(id);
        if(customer.isPresent()) {
            return customer.get();
        }
        else
            return null;
    }

    public Customer addCustomer(Customer customer)
    {
        return customer_repository.save(customer);
    }

    public Customer updateCustomer(Customer customer)
    {
        return customer_repository.save(customer);
    }

    public void deleteCustomer(String id)
    {
        Optional<Customer> customer = customer_repository.findById(id);
        if(customer.isPresent()) {
            customer_repository.delete(customer.get());
        }
    }
    
}
