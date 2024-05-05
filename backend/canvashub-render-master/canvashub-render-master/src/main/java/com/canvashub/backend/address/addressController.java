package com.canvashub.backend.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class addressController {

    @Autowired
    private addressServices address_service;

    @RequestMapping("/addresses")
    public List<Address> getAllAddress()
    {
        return address_service.getAllAddresses();
    }

    @RequestMapping(value="/addresses/{id}")
    public Address getAddress(@PathVariable String id)
    {
        return address_service.getAddress(id);
    }

    @RequestMapping(value="/add-address", method= RequestMethod.POST)
    public Address addAddress(@RequestBody Address address)
    {
        return address_service.addAddress(address);
    }

    @RequestMapping(value="/update-address", method=RequestMethod.PUT)
    public Address updateAddress(@RequestBody Address address)
    {
        return address_service.updateAddress(address);
    }

    @RequestMapping(value="/addresses/{id}", method=RequestMethod.DELETE)
    public void deleteAddress(@PathVariable String id)
    {
        address_service.deleteAddress(id);
    }
    
}
