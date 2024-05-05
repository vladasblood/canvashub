package com.example.canvashub.api;

import com.example.canvashub.model.Branch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface branchApi {

    @GET("/branches")
    Call<List<Branch>> getAllBranches();

    @GET(value = "/branches/{id}")
    Call<Branch> getBranch(@Path("id") String id);

    @POST(value = "/add-branch")
    Call<Branch> addBranch(@Body Branch branch);

    @PUT(value = "/update-branch")
    Call<Branch> updateBranch(@Body Branch branch);

    @DELETE(value = "/branches/{id}")
    Call<Branch> deleteBranch(@Path("id") String id);

}
