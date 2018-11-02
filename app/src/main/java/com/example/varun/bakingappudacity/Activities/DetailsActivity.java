package com.example.varun.bakingappudacity.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.varun.bakingappudacity.Adapters.RecipeDetailsPagerAdapter;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Fragments.IngredientFragment;
import com.example.varun.bakingappudacity.Fragments.StepsFragment;
import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;
import com.example.varun.bakingappudacity.Widget.WidgetUpdateService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    Context context;
    public Recipe recipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_activity);
        ButterKnife.bind(this);
        context = this;

        if( getIntent().getExtras() != null) {
            recipe  =  (Recipe) getIntent().getExtras().getSerializable(Constants.RECIPE);
            this.setTitle(recipe.getName());
            viewPager.setAdapter(new RecipeDetailsPagerAdapter(this, getSupportFragmentManager(), recipe));
            tabLayout.setupWithViewPager(viewPager);
            WidgetUpdateService.updatingWidget(this, recipe);
        }
        else {
            Toast.makeText(context, "No Data Received, Try again", Toast.LENGTH_SHORT).show();
        }

    }
}
