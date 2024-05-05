package com.canvashub.backend.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class storeServices {

    @Autowired
    private storeRepository store_repository;

    public List<Store> getAllStores()
    {
        List<Store>storeRecords = new ArrayList<>();
        store_repository.findAll().forEach(storeRecords::add);
        return storeRecords;
    }

    public Store getStore(String id)
    {
        Optional<Store> store = store_repository.findById(id);
        if(store.isPresent()) {
            return store.get();
        }
        else
            return null;
    }

    public Store addStore(Store store)
    {
        return store_repository.save(store);
    }

    public Store updateStore(Store store)
    {
        return store_repository.save(store);
    }

    public void deleteStore(String id)
    {
        Optional<Store> store = store_repository.findById(id);
        if(store.isPresent()) {
            store_repository.delete(store.get());
        }
    }

}
