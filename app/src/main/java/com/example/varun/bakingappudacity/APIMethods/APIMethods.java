package com.example.varun.bakingappudacity.APIMethods;

import com.example.varun.bakingappudacity.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIMethods {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
