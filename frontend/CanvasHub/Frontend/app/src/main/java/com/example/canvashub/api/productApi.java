package com.example.canvashub.api;

import com.example.canvashub.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface productApi {
    @GET("/products")
    Call<List<Product>> getAllProducts();

    @GET("/products/{id}")
    Call<Product> getProduct(@Path("id") String id);

    @POST("/add-product")
    Call<Product> addProducts(@Body Product products);

    @PUT("/update-product")
    Call<Product> updateProducts(@Body Product products);

    @DELETE("/products/{id}")
    Call<Product> deleteProduct(@Path("id") String id);
}
