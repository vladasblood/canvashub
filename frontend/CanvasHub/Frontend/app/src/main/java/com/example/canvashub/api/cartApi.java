package com.example.canvashub.api;

import com.example.canvashub.model.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface cartApi {

    @GET("/carts")
    Call<List<Cart>> getAllCarts();

    @GET(value = "/carts/{id}")
    Call<Cart> getCart(@Path("id") String id);

    @POST(value = "/add-cart")
    Call<Cart> addCart(@Body Cart cart);

    @PUT(value = "/update-cart")
    Call<Cart> updateCart(@Body Cart cart);

    @DELETE(value = "/carts/{id}")
    Call<Cart> deleteCart(@Path("id") String id);

}
