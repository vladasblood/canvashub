package com.example.canvashub.api;

import com.example.canvashub.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface categoryApi {

    @GET("/categories")
    Call<List<Category>> getAllCategories();

    @GET(value = "/categories/{id}")
    Call<Category> getCategory(@Path("id") String id);

    @POST(value = "/add-category")
    Call<Category> addCategory(@Body Category category);

    @PUT(value = "/update-category")
    Call<Category> updateCategory(@Body Category category);

    @DELETE(value = "/categories/{id}")
    Call<Category> deleteCategory(@Path("id") String id);

}
