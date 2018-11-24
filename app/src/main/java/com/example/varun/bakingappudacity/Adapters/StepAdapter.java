package com.example.varun.bakingappudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.varun.bakingappudacity.Activities.DetailsActivity;
import com.example.varun.bakingappudacity.Activities.StepDetailActivity;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Fragments.StepDetailFragment;
import com.example.varun.bakingappudacity.Models.Recipe;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    
    private Recipe recipe;
    private List<Step> steps;
    private final DetailsActivity activity;
    private final boolean twoPaneMode;

    public StepAdapter(DetailsActivity parent, Recipe recipe, boolean twoPane) {
        this.recipe = recipe;
        steps = recipe.getSteps();
        activity = parent;
        twoPaneMode = twoPane;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_step, parent, false);
        StepViewHolder viewHolder = new StepViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.stepTitle)
        TextView stepTitle;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            String stepNumber = steps.get(position).getId().toString();
            String stepShortDescription = steps.get(position).getShortDescription();
            stepTitle.setText(stepNumber + ". " + stepShortDescription);
        }

        @Override
        public void onClick(View v) {
            if (twoPaneMode) {
                Bundle args = new Bundle();
                args.putString(StepDetailFragment.ARG_ITEM_ID, steps.get(getAdapterPosition()).getId().toString());
                StepDetailFragment fragment = new StepDetailFragment();
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
                args.putString(Constants.VIDEO_URL_KEY, recipe.getSteps().get(getAdapterPosition()).getVideoURL());
                args.putString(Constants.DESCRIPTION_KEY, recipe.getSteps().get(getAdapterPosition()).getDescription());
                args.putParcelable(Constants.LIST_KEY, Parcels.wrap(recipe.getSteps()));
                args.putInt(Constants.STEP_KEY, getAdapterPosition());
            } else {

                Context context = v.getContext();
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putExtra(Constants.VIDEO_URL_KEY, recipe.getSteps().get(getAdapterPosition()).getVideoURL());
                intent.putExtra(Constants.DESCRIPTION_KEY, recipe.getSteps().get(getAdapterPosition()).getDescription());
                intent.putExtra(Constants.LIST_KEY, Parcels.wrap(recipe.getSteps()));
                intent.putExtra(Constants.STEP_KEY, getAdapterPosition());
                context.startActivity(intent);
            }
        }
    }
}
