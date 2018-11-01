package com.example.varun.bakingappudacity.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Fragments.IngredientFragment;
import com.example.varun.bakingappudacity.Fragments.StepsFragment;
import com.example.varun.bakingappudacity.Models.Recipe;

public class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {

    final int PAGES = 2;

    private Context context;
    private Recipe recipe;

    public RecipeDetailsPagerAdapter(Context context, FragmentManager fragmentManager, Recipe recipe) {
        super(fragmentManager);
        this.context = context;
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return IngredientFragment.newInstance(recipe);
            }
            case 1: {
                return StepsFragment.newInstance(recipe);
            }
            default:
                return StepsFragment.newInstance(recipe);
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ingredients";
            case 1:
                return "Steps";
            default:
                return "Error";
        }
    }
}
