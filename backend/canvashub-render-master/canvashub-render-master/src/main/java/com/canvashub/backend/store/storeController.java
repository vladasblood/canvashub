package com.canvashub.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class storeController {

    @Autowired
    private storeServices store_service;

    @RequestMapping("/stores")
    public List<Store> getAllStore()
    {
        return store_service.getAllStores();
    }

    @RequestMapping(value="/stores/{id}")
    public Store getStore(@PathVariable String id)
    {
        return store_service.getStore(id);
    }

    @RequestMapping(value="/add-store", method= RequestMethod.POST)
    public Store addStore(@RequestBody Store store)
    {
        return store_service.addStore(store);
    }

    @RequestMapping(value="/update-store", method=RequestMethod.PUT)
    public Store updateStore(@RequestBody Store store)
    {
        return store_service.updateStore(store);
    }

    @RequestMapping(value="/stores/{id}", method=RequestMethod.DELETE)
    public void deleteStore(@PathVariable String id)
    {
        store_service.deleteStore(id);
    }

}
