package com.example.varun.bakingappudacity.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    @BindView(R.id.rv_steps)
    RecyclerView recyclerView;

    Context context;
    List<Step> stepList;

    @SuppressLint("ValidFragment")
    public StepsFragment(List<Step> steps) {
        this.stepList = steps;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ingredients_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {

    }
}
