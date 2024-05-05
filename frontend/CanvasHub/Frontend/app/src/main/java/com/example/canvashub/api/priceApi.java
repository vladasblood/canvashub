package com.example.canvashub.api;

import com.example.canvashub.model.Price;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface priceApi {

    @GET("/prices")
    Call<List<Price>> getAllPrices();

    @GET(value = "/prices/{id}")
    Call<Price> getPrice(@Path("id") String id);

    @POST(value = "/add-price")
    Call<Price> addPrice(@Body Price price);

    @PUT(value = "/update-price")
    Call<Price> updatePrice(@Body Price price);

    @DELETE(value = "/prices/{id}")
    Call<Price> deletePrice(@Path("id") String id);

}
