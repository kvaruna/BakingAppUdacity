package com.example.varun.bakingappudacity.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.varun.bakingappudacity.Constants.Constants;
import com.example.varun.bakingappudacity.Models.Step;
import com.example.varun.bakingappudacity.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepVideo extends AppCompatActivity {

    private long mPlayerPosition = 0;
    private boolean mPlayerState = true;
    private SimpleExoPlayer exoPlayer;
    private TrackSelection.Factory trackSelFactory;
    private BandwidthMeter bandwidthMeter;
    private TrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    private List<Step> step;
    private int position;
    int nextStep;
    int prevStep;
    private Context context;

    @BindView(R.id.videoThumbnail)
    ImageView videoThumb;
    @BindView(R.id.videoPlayback)
    PlayerView videoPlayback;
    @BindView(R.id.longDesc)
    TextView longDescription;
    @BindView(R.id.previous)
    Button previous;
    @BindView(R.id.next)
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_activity);
        context = this;
        ButterKnife.bind(this);
        step = (List<Step>) getIntent().getExtras().getSerializable("Step");
        position = (int) getIntent().getExtras().getSerializable("Position");
        nextStep = position+1;
        Log.d("This is next","Step no"+nextStep);
        prevStep = position-1;
        Log.d("This is prev","Step no"+prevStep);
        Log.d("This is step","List Size"+step.size());
        if (!(position >=step.size()-1)) {
            String no = String.valueOf(nextStep);
            next.setText(Constants.NEXT + no);
        }else next.setVisibility(View.GONE);
        if (position==0){
            previous.setVisibility(View.GONE);
        }
        else if (position>2){
            String no = String.valueOf(prevStep);
            previous.setText(Constants.PREVIOUS + no);
        }
        handleButtons();
        this.setTitle(step.get(position).getShortDescription());
        if( step.get(position).getDescription() != null) {
            longDescription.setText(step.get(position).getDescription());
        } else {
            longDescription.setText("");
        }

        if( step.get(position).getVideoURL() != "") {
            String url = step.get(position).getVideoURL();
            Handler handler = new Handler();
            bandwidthMeter = new DefaultBandwidthMeter();
            trackSelFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            trackSelector = new DefaultTrackSelector(trackSelFactory);
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            dataSourceFactory = new DefaultDataSourceFactory(this, "BakingApp");
            mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(url), handler, null);

            videoPlayback.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(mPlayerState);
            exoPlayer.prepare(mediaSource);
            if(mPlayerPosition != 0) {
                exoPlayer.seekTo(mPlayerPosition);
                Log.e("StepVideoActivity", mPlayerPosition+"");
            }

        } else if (step.get(position).getThumbnailURL() != "") {
            final String url = step.get(position).getThumbnailURL();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                String contentType = urlConnection.getHeaderField("Content-Type");
                                if( contentType.startsWith("image/")) {
                                    Glide.with(context).load(url).into(videoThumb);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } else {
            videoPlayback.setVisibility(View.GONE);
            videoThumb.setVisibility(View.GONE);
        }
    }

    private void handleButtons() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepVideo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Step", (Serializable) step);
                bundle.putSerializable("Position",prevStep);
                intent.putExtras(bundle);
                context.startActivity(intent);
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StepVideo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Step", (Serializable) step);
                bundle.putSerializable("Position",nextStep);
                intent.putExtras(bundle);
                context.startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if( exoPlayer != null) {
            exoPlayer.release();
        }
    }

}
