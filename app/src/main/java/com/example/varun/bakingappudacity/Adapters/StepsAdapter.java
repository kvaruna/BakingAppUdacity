package com.example.varun.bakingappudacity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.varun.bakingappudacity.Activities.StepVideo;
import com.example.varun.bakingappudacity.Models.Ingredient;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    List<Step> stepList;
    Context context;
    int layout = R.layout.layout_steps_item;

    public StepsAdapter(List<Step> steps, Context context) {
        this.stepList = steps;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        StepsAdapter.ViewHolder viewHolder = new StepsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig().textColor(Color.BLACK)
                .endConfig()
                .buildRound(String.valueOf(position+1), context.getResources().getColor(R.color.badgeBkg));
        holder.descTitle.setText(stepList.get(holder.getAdapterPosition()).getShortDescription());
        holder.no.setImageDrawable(drawable);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepVideo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Step", (Serializable) stepList);
                bundle.putSerializable("Position", holder.getAdapterPosition());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (stepList != null) {
            return stepList.size();
        } else {
            return 0;
        }
    }

    public void updateAdapter(List<Ingredient> ingredients) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.descTitle)
        TextView descTitle;
        @BindView(R.id.no)
        ImageView no;
        @BindView(R.id.rootView)
        CardView rootView;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
