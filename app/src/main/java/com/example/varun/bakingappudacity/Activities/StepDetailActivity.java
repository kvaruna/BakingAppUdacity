package com.example.varun.bakingappudacity.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Fragments.StepDetailFragment;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;

import org.parceler.Parcels;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {
    
    private String videoUrl = "";
    private String desc = "";
    private int currentStep = 0;
    ArrayList<Step> steps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        if (getIntent().hasExtra(Constants.VIDEO_URL_KEY)) {
            videoUrl = getIntent().getStringExtra(Constants.VIDEO_URL_KEY);
            desc = getIntent().getStringExtra(Constants.DESCRIPTION_KEY);
            steps = Parcels.unwrap(getIntent().getParcelableExtra(Constants.LIST_KEY));
            currentStep = getIntent().getIntExtra(Constants.STEP_KEY, 0);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(Constants.VIDEO_URL_KEY, videoUrl);
            arguments.putString(Constants.DESCRIPTION_KEY, desc);
            arguments.putParcelable(Constants.LIST_KEY, Parcels.wrap(steps));
            arguments.putInt(Constants.STEP_KEY, currentStep);
            arguments.putString(StepDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(StepDetailFragment.ARG_ITEM_ID));
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
