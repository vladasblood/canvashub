package com.example.canvashub.api;

import com.example.canvashub.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface storeApi {

    @GET("/stores")
    Call<List<Store>> getAllStores();

    @GET(value = "/stores/{id}")
    Call<Store> getStore(@Path("id") String id);

    @POST(value = "/add-store")
    Call<Store> addStore(@Body Store store);

    @PUT(value = "/update-store")
    Call<Store> updateStore(@Body Store store);

    @DELETE(value = "/stores/{id}")
    Call<Store> deleteStore(@Path("id") String id);

}
