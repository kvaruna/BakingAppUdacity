package com.example.varun.bakingappudacity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.varun.bakingappudacity.APIClient.APIClient;
import com.example.varun.bakingappudacity.APIMethods.APIMethods;
import com.example.varun.bakingappudacity.Activities.DataDownloaderIdlingResource;
import com.example.varun.bakingappudacity.Adapters.RecipeAdapter;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recepies)
    RecyclerView rv_recepies;
    @BindView(R.id.loader)
    ProgressBar loader;
    @BindView(R.id.heading)
    TextView heading;
    RecipeAdapter adapter;
    Context context;
    private APIMethods apiMethods;
    List<Recipe> recipeList;
    LinearLayoutManager linearLayoutManager;
    @Nullable
    public DataDownloaderIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);
        apiMethods = APIClient.getClient().create(APIMethods.class);
        getRecipesList();
        setUpLayoutManager();
    }
    @VisibleForTesting
    @NonNull
    public DataDownloaderIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new DataDownloaderIdlingResource();
        }
        return mIdlingResource;
    }
    private void getRecipesList() {
        Call<List<Recipe>> getRecipes = apiMethods.getRecipes();
        getRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                setupRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
    private void setUpLayoutManager(){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        boolean isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if(isTablet){
            if(isLandscape){
                linearLayoutManager = new GridLayoutManager(this, 3);
            }
            else{
                linearLayoutManager = new GridLayoutManager(this, 2);
            }

        }
        else{
            if(isLandscape){
                linearLayoutManager = new GridLayoutManager(this, 2);
            }
            else{
                linearLayoutManager = new GridLayoutManager(this, 1);
            }
        }
    }
    private void setupRecyclerView() {
        rv_recepies.setLayoutManager(linearLayoutManager);
        adapter = new RecipeAdapter(recipeList, context);
        rv_recepies.setAdapter(adapter);
        rv_recepies.setVisibility(View.VISIBLE);
        heading.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
