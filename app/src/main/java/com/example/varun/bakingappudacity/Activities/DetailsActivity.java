package com.example.varun.bakingappudacity.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.bakingappudacity.Adapters.StepAdapter;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;
import com.example.varun.bakingappudacity.Widget.WidgetUpdate;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    private boolean twoPaneMode;
    Context context;
    public Recipe recipe;
    @BindView(R.id.tv_ingredient)
    public TextView tv_ingredient;
    @BindView(R.id.rv_steps)
    public RecyclerView rv_steps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_activity_new);
        ButterKnife.bind(this);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().hasExtra(Constants.RECIPE_ACT_KEY)) {
            recipe = Parcels.unwrap(getIntent().getParcelableExtra(Constants.RECIPE_ACT_KEY));
            this.setTitle(recipe.getName());
            addWidget();
        }
        if (findViewById(R.id.item_detail_container) != null) {
            twoPaneMode = true;
        }
        setupIngredients();
        setupRecyclerView();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void setupIngredients() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            result.append("- ");
            result.append(recipe.getIngredients().get(i).getIngredient());
            result.append(" (");
            result.append(recipe.getIngredients().get(i).getQuantity());
            result.append(" ");
            result.append(recipe.getIngredients().get(i).getMeasure());
            result.append(").");
            if (i != (recipe.getIngredients().size() - 1)) {
                result.append("\n");
            }
        }
        tv_ingredient.setText(result.toString());
    }

    private void addWidget() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            result.append("- ");
            result.append(recipe.getIngredients().get(i).getIngredient());
            result.append(" (");
            result.append(recipe.getIngredients().get(i).getQuantity());
            result.append(" ");
            result.append(recipe.getIngredients().get(i).getMeasure());
            result.append(").");
            if (i != (recipe.getIngredients().size() - 1)) {
                result.append("\n");
            }
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetUpdate.class));
        if (appWidgetIds.length == 0) {
            //If widget is not added

        } else {
            for (int i = 0; i < appWidgetIds.length; i++) {
                WidgetUpdate.updateAppWidget(this, appWidgetManager, appWidgetIds[i], recipe.getName() + " - Ingredients", result.toString());
            }
        }
    }

    private void setupRecyclerView() {
        StepAdapter stepAdapter = new StepAdapter(this, recipe, twoPaneMode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_steps.setLayoutManager(linearLayoutManager);
        rv_steps.setAdapter(stepAdapter);
        rv_steps.setFocusable(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.RECIPE_ACT_KEY, Parcels.wrap(recipe));
    }

}
