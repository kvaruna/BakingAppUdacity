package com.example.varun.bakingappudacity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.varun.bakingappudacity.APIClient.APIClient;
import com.example.varun.bakingappudacity.APIMethods.APIMethods;
import com.example.varun.bakingappudacity.Models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_recepies)
    RecyclerView rv_recepies;
    private APIMethods apiMethods;
    List<Recipe> recipeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiMethods = APIClient.getClient().create(APIMethods.class);
        getRecipesList();
    }

    private void getRecipesList() {
        Call<List<Recipe>> getRecipes = apiMethods.getRecipes();
        getRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeList = response.body();
                Toast.makeText(MainActivity.this, "This is size"+recipeList.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("This is error",": "+t.getMessage());
            }
        });
    }

}
