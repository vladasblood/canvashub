package com.example.canvashub.api;

import com.example.canvashub.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface customerApi {
    @GET("/customers")
    Call<List<Customer>> getAllCustomers();

    @GET("/customers/{id}")
    Call<Customer> getCustomer(@Path("id") String id);

    @POST("/add-customer")
    Call<Customer> addCustomer(@Body Customer customer);

    @PUT("/update-customer")
    Call<Customer> updateCustomer(@Body Customer customer);

    @DELETE("/customers/{id}")
    Call<Customer> deleteCustomer(@Path("id") String id);
}
