package com.canvashub.backend.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class addressServices {

    @Autowired
    private addressRepository address_repository;

    public List<Address> getAllAddresses()
    {
        List<Address>addressRecords = new ArrayList<>();
        address_repository.findAll().forEach(addressRecords::add);
        return addressRecords;
    }

    public Address getAddress(String id)
    {
        Optional<Address> address = address_repository.findById(id);
        if(address.isPresent()) {
            return address.get();
        }
        else
            return null;
    }

    public Address addAddress(Address address)
    {
        return address_repository.save(address);
    }

    public Address updateAddress(Address address)
    {
        return address_repository.save(address);
    }

    public void deleteAddress(String id)
    {
        Optional<Address> address = address_repository.findById(id);
        if(address.isPresent()) {
            address_repository.delete(address.get());
        }
    }
    
}
