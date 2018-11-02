package com.example.varun.bakingappudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    int layout = R.layout.layout_recipe_title;
    List<Recipe> recipeList;
    Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText(recipeList.get(holder.getAdapterPosition()).getName());
        holder.servings.setText(context.getResources().getString(R.string.servings)+recipeList.get(holder.getAdapterPosition()).getServings());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailsActivity(recipeList.get(holder.getAdapterPosition()));
            }
        });
    }

    private void openDetailsActivity(Recipe recipe) {
        Intent intent = new Intent(context,DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.RECIPE, recipe);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if( recipeList != null) {
            return recipeList.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.rootView)
        CardView rootView;
        @BindView(R.id.servings)
        TextView servings;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
