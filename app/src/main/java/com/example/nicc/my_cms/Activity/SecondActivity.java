package com.example.nicc.my_cms.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nicc.my_cms.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

public class SecondActivity extends AppCompatActivity {

    SimpleExoPlayer exoPlayer;
    PlayerView exoPlayerView;
    ExtractorMediaSource mediaSource;
    Integer position;
    final static String TAG = "Another_c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_activity);
        position = getIntent().getIntExtra("position",0);
        Log.d(TAG,"position = " + position);
        hideForActionBar();

    }

    @Override
    protected void onStart() {
        super.onStart();
    if(position == 0){
      position1();
    }else if (position == 1){
        try {
            position2();
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }
    }

    }
    void hideForActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    void position1(){
        exoPlayerView = findViewById(R.id.pv2);
        exoPlayerView.setUseController(false);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        exoPlayerView.setPlayer(exoPlayer);

        RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(SecondActivity.this);
        try {
            rawResourceDataSource.open(new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.covid)));
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }


        DataSource.Factory factory = new DefaultDataSourceFactory(this,"Main2Acitivy");
        mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());

        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);

        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
            @Override
            public void onLoadingChanged(boolean isLoading) {}

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState == Player.STATE_ENDED){
                    finish();
                }
            }
            @Override
            public void onRepeatModeChanged(int repeatMode) {}
            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
            @Override
            public void onPlayerError(ExoPlaybackException error) {}
            @Override
            public void onPositionDiscontinuity(int reason) {}
            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters){}
            @Override
            public void onSeekProcessed() {}
        });
    }

    void position2() throws RawResourceDataSource.RawResourceDataSourceException {
     exoPlayerView = findViewById(R.id.pv2);
     exoPlayerView.setUseController(false);

     exoPlayer = ExoPlayerFactory.newSimpleInstance(SecondActivity.this,new DefaultTrackSelector());
     exoPlayerView.setPlayer(exoPlayer);

     RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(SecondActivity.this);
     rawResourceDataSource.open(new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.covid_2)));

     DataSource.Factory factory = new DefaultDataSourceFactory(SecondActivity.this, "position2");
     mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(rawResourceDataSource.getUri());

     exoPlayer.prepare(mediaSource);
     exoPlayer.setPlayWhenReady(true);

     exoPlayer.addListener(new Player.EventListener() {
         @Override
         public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {}
         @Override
         public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}
         @Override
         public void onLoadingChanged(boolean isLoading) {}
        @Override
         public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
             if(playbackState == Player.STATE_ENDED){
                 finish();
             }
        }
         @Override
         public void onRepeatModeChanged(int repeatMode) {}
         @Override
         public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
         @Override
         public void onPlayerError(ExoPlaybackException error) {}
         @Override
         public void onPositionDiscontinuity(int reason) {}
        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
         @Override
         public void onSeekProcessed() {}
     });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"ExoPlayer_release 됨");
        exoPlayerView.setPlayer(null);
        exoPlayer.release();
        exoPlayer = null;
    }
}
