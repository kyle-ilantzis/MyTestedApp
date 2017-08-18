package com.mytestedapp.rest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestService {

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/profile")
    Call<ProfileResponse> profile();
}
