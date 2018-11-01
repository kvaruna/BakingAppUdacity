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

import com.example.varun.bakingappudacity.Fragments.IngredientFragment;
import com.example.varun.bakingappudacity.Fragments.StepsFragment;
import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;

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
        if (getIntent().hasExtra("Recipe"))
        {
            this.recipe = getIntent().getParcelableExtra("Recipe");
            this.setTitle(context.getResources().getString(R.string.lets_key)+recipe.getName());
            for (Ingredient ingredient : recipe.getIngredients()){
                Log.d("This is something",""+ingredient.getIngredient());
            }
            //setUpTabs();
        }

    }

    private void setUpTabs() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return new IngredientFragment(recipe.getIngredients());
                    case 1:
                        return new StepsFragment(recipe.getSteps());
                    default:
                        return new StepsFragment(recipe.getSteps());
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                return POSITION_NONE;
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
        });
        viewPager.getAdapter().notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }
}
