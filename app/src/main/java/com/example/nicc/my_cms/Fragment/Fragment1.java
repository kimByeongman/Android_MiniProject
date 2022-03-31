package com.example.nicc.my_cms.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

import lombok.SneakyThrows;


public class Fragment1 extends Fragment {

    final static String TAG = "Fragment_1";
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    ExtractorMediaSource mediaSource;
    boolean mPosition = true;
    private static Integer PERCENTAGE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video,container,false);

        return view;
    }

    @SneakyThrows
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = getView().findViewById(R.id.pv2);
        playerView.setUseController(false );

        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        while (!Thread.currentThread().isInterrupted()){
                            PERCENTAGE+=1;
                            Thread.sleep(1000);
                            Log.d(TAG, /*로그를 쉽게 확인하기 위해 PERCENTAGE 를 msg에 PERCENT 라고 적음*/"PERCENT = " + PERCENTAGE);
                            ProgressBar percent = (ProgressBar)view.findViewById(R.id.percentage);
                            percent.setMax(44);
                            percent.setProgress(PERCENTAGE);
                            if(PERCENTAGE >= 45){
                                currentThread().interrupt();
                                PERCENTAGE = 0;
                            }else if (!mPosition){
                                currentThread().interrupt();
                                PERCENTAGE = 0;
                            }
                        }
                    }catch (Throwable t){
                        Log.d(TAG,"Error = " + t.getMessage());
                    }
                }
            }
        }.start();

        position_1();

    }


    void position_1() throws RawResourceDataSource.RawResourceDataSourceException {

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),new DefaultTrackSelector());
        playerView.setPlayer(exoPlayer);

        RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(getActivity());
        rawResourceDataSource.open(new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.covid_2)));

        DataSource.Factory factory = new DefaultDataSourceFactory(getActivity(),"position2");
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

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FirstFragment firstFragment = new FirstFragment();
                    fragmentManager.beginTransaction().replace(R.id.rel,firstFragment).commit();
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
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"exoPlayer_release 되었음=");
        mPosition = false;
        exoPlayer.release();
    }
}
