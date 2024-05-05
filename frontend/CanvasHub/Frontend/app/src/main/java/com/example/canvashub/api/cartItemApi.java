package com.example.canvashub.api;

import com.example.canvashub.model.CartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface cartItemApi {

    @GET("/cartItems")
    Call<List<CartItem>> getAllCartItems();

    @GET(value = "/cartItems/{id}")
    Call<CartItem> getCartItem(@Path("id") String id);

    @POST(value = "/add-cartItem")
    Call<CartItem> addCartItem(@Body CartItem cartitem);

    @PUT(value = "/update-cartItem")
    Call<CartItem> updateCartItem(@Body CartItem cartitem);

    @DELETE(value = "/cartItems/{id}")
    Call<CartItem> deleteCartItem(@Path("id") String id);

}
