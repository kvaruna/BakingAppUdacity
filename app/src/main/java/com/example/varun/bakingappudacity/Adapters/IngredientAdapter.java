package com.example.varun.bakingappudacity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    int layout = R.layout.layout_ingredient_item;
    List<Ingredient> ingredientList;
    Context context;

    public IngredientAdapter(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context =context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        IngredientAdapter.ViewHolder viewHolder = new IngredientAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.quantityValue.setText(ingredientList.get(holder.getAdapterPosition()).getQuantity());
        holder.measureValue.setText(ingredientList.get(holder.getAdapterPosition()).getMeasure());
        holder.ingredientValue.setText(ingredientList.get(holder.getAdapterPosition()).getIngredient());
    }


    @Override
    public int getItemCount() {
        if( ingredientList != null) {
            return ingredientList.size();
        } else {
            return 0;
        }
    }

    public void updateAdapter(List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.measure_value)
        TextView measureValue;
        @BindView(R.id.ingredients_value)
        TextView ingredientValue;
        @BindView(R.id.quantity_value)
        TextView quantityValue;
         ViewHolder(View itemView) {
             super(itemView);
             ButterKnife.bind(this,itemView);

        }
    }
}
