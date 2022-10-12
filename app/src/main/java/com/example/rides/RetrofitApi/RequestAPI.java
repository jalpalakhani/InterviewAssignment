package com.example.rides.RetrofitApi;

import com.example.rides.responseModels.RandomVehicleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestAPI {

    @GET("/api/vehicle/random_vehicle")
    Call<ArrayList<RandomVehicleResponse>> getRandomVehicleRequest(@Query("size") int size);


}