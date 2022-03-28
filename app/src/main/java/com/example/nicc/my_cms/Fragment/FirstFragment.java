package com.example.nicc.my_cms.Fragment;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.SneakyThrows;


public class FirstFragment extends Fragment  {

    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    ExtractorMediaSource extractorMediaSource;
    Integer videoIndex = 12;
    ProgressBar percentage;
    long videoWatchedTime;

    final static String TAG = "FirstFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        return view;

    }
    @SneakyThrows
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.play_first);
        playerView.setUseController(false);

        videoWatchedTime =0;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                videoWatchedTime = exoPlayer.getCurrentPosition()/1000;
            }
        },1000);




        playVideo();
    }
    void playVideo(){
        AssetManager assetManager = getActivity().getAssets();

        try {
            Log.d(TAG,"호출되었음");
            InputStream is = assetManager.open("Jsons/VideoUrl.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null){
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            final String jsonData = buffer.toString();

            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray categoriesArray = jsonObject.getJSONArray("categories");
            JSONObject object = categoriesArray.getJSONObject(0);
            JSONArray videoArray = object.getJSONArray("videos");

            final  JSONObject videoData = videoArray.getJSONObject(videoIndex);
            String sources = videoData.getString("sources");
            final String thumb = videoData.getString("thumb");
            sources = sources.replace("[\"", "");
            sources = sources.replace("\"]", "");
            sources = sources.replace("\\/", "/");

            final  String videoUrl = sources;

            Uri videoUri = Uri.parse(videoUrl);

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),new DefaultTrackSelector());
            playerView.setPlayer(exoPlayer);

            DataSource.Factory factory = new DefaultDataSourceFactory(getActivity(), "FirstFragment");
            extractorMediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);

            exoPlayer.prepare(extractorMediaSource);
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
                        try {
                            --videoIndex;
                            final JSONObject videoData = videoArray.getJSONObject(videoIndex);
                            String sources = videoData.getString("sources");
                            sources = sources.replace("[\"", "");
                            sources = sources.replace("\"]", "");
                            sources = sources.replace("\\/", "/");
                            final String videoUrl = sources;
                            Uri videoUri = Uri.parse(videoUrl);
                            extractorMediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(videoUri);

                            exoPlayer.prepare(extractorMediaSource);
                            exoPlayer.setPlayWhenReady(true);

                            if (videoIndex == (-1)) {
                                videoIndex = 12;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

        }catch (Exception e){

        }
    }
    void setProgressBar(float percentageValue){
        percentage.setProgress((int)percentageValue * 100);
        if(percentage.getVisibility() == View.GONE){
            percentage.setVisibility(View.VISIBLE);
        }else if(percentageValue == 1){
            getActivity().runOnUiThread(()-> YoYo.with(Techniques.FadeOut).duration(500).onEnd(animator -> percentage.setVisibility(View.GONE)).playOn(percentage));
        }
    }

}
