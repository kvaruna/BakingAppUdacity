package com.example.varun.bakingappudacity.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;

import java.util.List;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(getApplicationContext(), intent);
    }

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Recipe recipe;
        Context context;

        public RecipeRemoteViewsFactory(Context applicationContext, Intent intent) {
            this.context = applicationContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            recipe = RecipeWidget.recipe;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (recipe.getIngredients() != null)
                return recipe.getIngredients().size();
            else
                return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            List<Ingredient> ingredients = recipe.getIngredients();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_widget_item);
            remoteViews.setTextViewText(R.id.ingredient_name, String.valueOf(ingredients.get(i).getIngredient()));
            remoteViews.setTextViewText(R.id.ingredient_value, String.valueOf(ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure()));
            remoteViews.setTextViewText(R.id.recipeName, recipe.getName());
            Intent intent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.rootView_widget, intent);
            return remoteViews;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
