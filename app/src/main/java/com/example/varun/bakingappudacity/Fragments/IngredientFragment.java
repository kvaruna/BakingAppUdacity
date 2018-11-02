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

import com.example.varun.bakingappudacity.Adapters.IngredientAdapter;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class IngredientFragment extends Fragment {
    @BindView(R.id.rv_steps)
    RecyclerView recyclerView;

    Context context;
    IngredientAdapter adapter;
    public IngredientFragment() { }

    public static IngredientFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.RECIPE, recipe);
        IngredientFragment fragment = new IngredientFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ingredients_fragment, container, false);
        Recipe recipe = (Recipe) getArguments().getSerializable(Constants.RECIPE);
        ButterKnife.bind(this, view);
        context = getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IngredientAdapter(recipe.getIngredients(),context);
        recyclerView.setAdapter(adapter);
        if( recipe != null) {
            adapter.updateAdapter(recipe.getIngredients());
        } else {
            Log.e("Fragment", "Recipe is null");
        }
        return view;
    }
}
