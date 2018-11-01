package com.example.varun.bakingappudacity.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Adapters.IngredientAdapter;
import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    @BindView(R.id.rv_steps)
    RecyclerView recyclerView;

    Context context;
    List<Ingredient> ingredientList;
    IngredientAdapter adapter;

    @SuppressLint("ValidFragment")
    public IngredientFragment(List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        Log.d("This is Size","---"+ingredients.size());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ingredients_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        //setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientAdapter(ingredientList,context);
        recyclerView.setAdapter(adapter);
    }
}
