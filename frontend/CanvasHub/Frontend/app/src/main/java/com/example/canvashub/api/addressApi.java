package com.example.canvashub.api;

import com.example.canvashub.model.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface addressApi {

    @GET("/addresses")
    Call<List<Address>> getAllAddresses();

    @GET("/addresses/{id}")
    Call<Address> getAddress(@Path("id") String id);

    @POST("/add-address")
    Call<Address> addAddress(@Body Address address);

    @PUT("/update-address")
    Call<Address> updateAddress(@Body Address address);

    @DELETE("/address/{id}")
    Call<Address> deleteAddress(@Path("id") String id);

}
