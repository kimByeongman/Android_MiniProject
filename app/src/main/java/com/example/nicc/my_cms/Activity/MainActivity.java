package com.example.nicc.my_cms.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.nicc.my_cms.Adapter.ReAdapter;
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
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SimpleExoPlayer exoPlayer;
    PlayerView exoPlayerView;
    ImageButton btn;
    ImageButton btnHide;
    List<Integer> img;
    ReAdapter reAdapter;
    ExtractorMediaSource mediaSource;
    final static String TAG = "WHAT is THIS?";

    Context context = MainActivity.this;
    int videoIndex = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exoPlayerView = findViewById(R.id.pv);
        recyclerView = findViewById(R.id.rec);
        btnHide = findViewById(R.id.btnHide);
        btn = findViewById(R.id.btn);

        exoPlayerView.setUseController(false);

        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.VISIBLE);
                btnHide.setVisibility(View.GONE);
                btn.setVisibility(View.VISIBLE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
                btnHide.setVisibility(View.VISIBLE);
            }
        });


        hideForActionBar();
        RecycleView();
        onExoPlayer();
    }

    void hideForActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    void onExoPlayer() {
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("Jsons/VideoUrl.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            final String jsonData = buffer.toString();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray categoriesArray = jsonObject.getJSONArray("categories");
            JSONObject object = categoriesArray.getJSONObject(0);
            JSONArray videoArray = object.getJSONArray("videos");

            Log.d(TAG, "object = ?" + object);

            final JSONObject videoData = videoArray.getJSONObject(videoIndex);
            Log.d(TAG, "videoData = ?" + videoData);
            String sources = videoData.getString("sources");
            final String thumb = videoData.getString("thumb");
            sources = sources.replace("[\"", "");
            sources = sources.replace("\"]", "");
            sources = sources.replace("\\/", "/");

            final String videoUrl = sources;

            Log.d(TAG,"videoUrl = ? " + videoUrl);
            Uri videoUri = Uri.parse(videoUrl);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
            exoPlayerView.setPlayer(exoPlayer);

            DataSource.Factory factory = new DefaultDataSourceFactory(this,"ExoPlayer");
            mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);

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
                    if(playbackState == Player.STATE_ENDED) {
                        Log.d(TAG, "videoIndex = ? " + videoIndex);
                        Log.d(TAG, "동영상 끝남 = ? " + playbackState);

                        try {
                            --videoIndex;
                            Log.d(TAG, "videoIndex = ?" + videoIndex);
                            final JSONObject videoData = videoArray.getJSONObject(videoIndex);
                            String sources = videoData.getString("sources");
                            sources = sources.replace("[\"", "");
                            sources = sources.replace("\"]", "");
                            sources = sources.replace("\\/", "/");
                            final String videoUrl = sources;
                            Uri videoUri = Uri.parse(videoUrl);
                            Log.d(TAG, "source  = 2 " + sources);
                            mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);

                            exoPlayer.prepare(mediaSource);
                            exoPlayer.setPlayWhenReady(true);

                            if (videoIndex == (-1)) {
                                videoIndex = 12;
                                Log.d(TAG, "videoIndex = Changed " + videoIndex);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onRepeatModeChanged(int repeatMode) {}
                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {}
                public void onPlayerError(ExoPlaybackException error) {}
                @Override
                public void onPositionDiscontinuity(int reason) {}
                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
                @Override
                public void onSeekProcessed() {}
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();}

    }

    @Override
    protected void onStart() {
        super.onStart();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayerView.setPlayer(null);
        exoPlayer.release();
        exoPlayer = null;

        Log.d(TAG,"onDestroy 호출되었음");

    }

    void RecycleView() {

        recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        reAdapter = new ReAdapter();

        img = new ArrayList<>();

        img.add(R.drawable.image9);
        img.add(R.drawable.image1);
        img.add(R.drawable.image2);
        img.add(R.drawable.image3);
        img.add(R.drawable.image4);
        img.add(R.drawable.image5);
        img.add(R.drawable.image6);
        img.add(R.drawable.image7);
        img.add(R.drawable.image8);
        img.add(R.drawable.image10);
        img.add(R.drawable.image11);
        img.add(R.drawable.image12);
        img.add(R.drawable.image13);
        img.add(R.drawable.image14);

        Log.d(TAG,"img.get(0)  = " + img);

        reAdapter.setImage(img, MainActivity.this, position -> {
            exoPlayer.setPlayWhenReady(false);
            Log.d(TAG,"position = " + position);
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
        recyclerView.setAdapter(reAdapter);
    }
}
