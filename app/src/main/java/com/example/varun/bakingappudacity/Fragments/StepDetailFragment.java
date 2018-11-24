package com.example.varun.bakingappudacity.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import java.util.ArrayList;

public class StepDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    // Objects
    private static String TAG = StepDetailFragment.class.getSimpleName();
    ArrayList<Step> steps = new ArrayList<>();
    String videoUrl = "";
    String desc = "";
    private SimpleExoPlayer exoPlayer;
    private int totalSteps;
    private int currentStep = 0;
    private long currentPlayingPosition;
    private boolean readyToPlay = true;

    // Views
    private MediaSessionCompat mediaSession;
    private PlayerView exoPlayerView;
    private TextView tv_details;
    private TextView noImgAvailable;
    private TextView btnNext;
    private TextView btnPrevious;
    private TextView tv_current;
    private ImageView iv_recipeStepDetails;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_step_detail, container, false);

        // Getting Step Values
        videoUrl = getArguments().getString(Constants.VIDEO_URL_KEY);
        desc = getArguments().getString(Constants.DESCRIPTION_KEY);
        steps = Parcels.unwrap(getArguments().getParcelable(Constants.LIST_KEY));
        currentStep = getArguments().getInt(Constants.STEP_KEY);
        totalSteps = steps.size();

        // When device rotate -> load values from saveInstance
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.CURRENT_VIDEO_PLAY_POSITION_KEY)) {
            videoUrl = savedInstanceState.getString(Constants.VIDEO_URL_KEY);
            desc = savedInstanceState.getString(Constants.DESCRIPTION_KEY);
            steps = Parcels.unwrap((Parcelable) savedInstanceState.getParcelable(Constants.LIST_KEY));
            currentStep = savedInstanceState.getInt(Constants.STEP_KEY);
            currentPlayingPosition = savedInstanceState.getLong(Constants.CURRENT_VIDEO_PLAY_POSITION_KEY);
            readyToPlay = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY_KEY);
            Log.d(TAG, "onCreateView: savedInstanceState Called!!!! | currentPlayingPosition -> " + currentPlayingPosition + "readyToPlay -> " + readyToPlay);
        }

        // Find Views
        exoPlayerView = rootView.findViewById(R.id.player_view);
        tv_details = rootView.findViewById(R.id.item_detail);
        noImgAvailable = rootView.findViewById(R.id.iv_no_img);
        btnNext = (TextView) rootView.findViewById(R.id.tv_next_button);
        btnPrevious = (TextView) rootView.findViewById(R.id.tv_previous_button);
        tv_current = (TextView) rootView.findViewById(R.id.tv_current_step);
        iv_recipeStepDetails = (ImageView) rootView.findViewById(R.id.iv_recipe_step_details);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep < totalSteps - 1) {
                    // Stop Playing
                    if (exoPlayer != null) {
                        exoPlayer.stop();
                        exoPlayer.release();
                        exoPlayer = null;
                        mediaSession.setActive(false);
                    }
                    // Reset Position and Play status on New Step
                    currentPlayingPosition = 0;
                    readyToPlay = true;

                    currentStep = currentStep + 1;
                    populateStepValues(
                            steps.get(currentStep).getDescription(),
                            steps.get(currentStep).getVideoURL(),
                            steps.get(currentStep).getThumbnailURL(),
                            String.valueOf("Step # " + currentStep));
                }
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep > 0) {
                    // Stop Playing
                    if (exoPlayer != null) {
                        exoPlayer.stop();
                        exoPlayer.release();
                        exoPlayer = null;
                        mediaSession.setActive(false);
                    }
                    // Reset Position and Play status on New Step
                    currentPlayingPosition = 0;
                    readyToPlay = true;

                    currentStep = currentStep - 1;
                    populateStepValues(
                            steps.get(currentStep).getDescription(),
                            steps.get(currentStep).getVideoURL(),
                            steps.get(currentStep).getThumbnailURL(),
                            String.valueOf("Step # " + currentStep));
                }
            }
        });


        // If phone goes to landscape --Then--> Maximize the PlayerView to FullScreen
        // If a Phone && on Landscape
        if (!getResources().getBoolean(R.bool.isTablet) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Set Activity Screen to Immersive --> https://developer.android.com/training/system-ui/immersive.htm
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
            // Hide ActionBar
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            // Hide all views except exoPlayerView, So it become a fullscreen video
            btnNext.setVisibility(View.GONE);
            btnPrevious.setVisibility(View.GONE);
            tv_current.setVisibility(View.GONE);
            tv_details.setVisibility(View.GONE);
            // Make exoPlayerView Width and Height match_parent in size.
            exoPlayerView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            populateStepValues(
                    steps.get(currentStep).getDescription(),
                    steps.get(currentStep).getVideoURL(),
                    steps.get(currentStep).getThumbnailURL(),
                    String.valueOf("Step # " + currentStep));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            populateStepValues(
                    steps.get(currentStep).getDescription(),
                    steps.get(currentStep).getVideoURL(),
                    steps.get(currentStep).getThumbnailURL(),
                    String.valueOf("Step # " + currentStep));
        }
    }

    private void populateStepValues(String description, String videoUrl, String thumbnailUrl, String currentStep) {

        if (videoUrl.equals("")) {
            exoPlayerView.setVisibility(View.INVISIBLE);
            noImgAvailable.setVisibility(View.VISIBLE);

        } else {
            exoPlayerView.setVisibility(View.VISIBLE);
            noImgAvailable.setVisibility(View.GONE);
            initializePlayer(videoUrl);
        }

        // Populate Description
        tv_details.setText(description);

        // Populate Counting TextView -> tv_current
        tv_current.setText(currentStep);

        // Populate ThumbnailUrl
        try {
            if (!thumbnailUrl.equals("")) {
                iv_recipeStepDetails.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(thumbnailUrl).into(iv_recipeStepDetails);
            }
        } catch (
                Exception e) {
            Log.d(TAG, "populateStepValues: " + e.toString());
        }
    }

    private void initializePlayer(String videoUrl) {
        // Preparing Media Session
        mediaSession = new MediaSessionCompat(getActivity(), TAG);
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);
        // Preparing PlaybackState for media buttons
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setActive(true);

        if (exoPlayer == null) {
            // Preparing Player
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            // Preparing the MediaSource
            String userAgent = Util.getUserAgent(getActivity(), getContext().getString(R.string.app_name));
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), userAgent);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(
                    Uri.parse(videoUrl),
                    dataSourceFactory,
                    extractorsFactory,
                    null,
                    null);

            exoPlayer.prepare(mediaSource);

            // Forward to Position
            if (currentPlayingPosition != 0) {
                exoPlayer.seekTo(currentPlayingPosition);
                Log.d(TAG, "initializePlayer: currentPlayingPosition -> " + currentPlayingPosition);
            }
            // Stop or Play as user decided
            exoPlayer.setPlayWhenReady(readyToPlay);
            Log.d(TAG, "initializePlayer: readyToPlay -> " + readyToPlay);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            readyToPlay = exoPlayer.getPlayWhenReady();
            currentPlayingPosition = exoPlayer.getCurrentPosition();
        }
        outState.putString(Constants.VIDEO_URL_KEY, videoUrl);
        outState.getString(Constants.DESCRIPTION_KEY, desc);
        outState.putParcelable(Constants.LIST_KEY, Parcels.wrap(steps));
        outState.putInt(Constants.STEP_KEY, currentStep);
        outState.putLong(Constants.CURRENT_VIDEO_PLAY_POSITION_KEY, currentPlayingPosition);
        outState.putBoolean(Constants.PLAY_WHEN_READY_KEY, readyToPlay);
        Log.d(TAG, "onSaveInstanceState: Called" + currentPlayingPosition + " " + readyToPlay);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            readyToPlay = exoPlayer.getPlayWhenReady();
            currentPlayingPosition = exoPlayer.getCurrentPosition();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
