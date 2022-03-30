package com.example.nicc.my_cms.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.nicc.my_cms.Adapter.ReAdapter;
import com.example.nicc.my_cms.Fragment.FirstFragment;
import com.example.nicc.my_cms.Fragment.Fragment0;
import com.example.nicc.my_cms.Fragment.Fragment1;
import com.example.nicc.my_cms.R;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;

    ImageButton btn;
    ImageButton btnHide;
    List<Integer> img;
    ReAdapter reAdapter;
    Fragment0 fragment0;
    Fragment1 fragment1;
    FirstFragment firstFragment;
    final static String TAG = "WHAT is THIS?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec);
        btnHide = findViewById(R.id.btnHide);
        btn = findViewById(R.id.btn);

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

        firstFragment = new FirstFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.rel,firstFragment, "first").commit();



        hideForActionBar();
        RecycleView();

        CheckTypesTask task = new CheckTypesTask();
        task.execute();

    }



    void hideForActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart 호출 되었음");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume 호출되었음");
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
            Log.d(TAG,"position = " + position);

            recyclerView.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            btnHide.setVisibility(View.VISIBLE);
             fragment0 = new Fragment0();
             fragment1 = new Fragment1();

            if(position == 0){
                getSupportFragmentManager().beginTransaction().replace( R.id.rel,fragment0, "position_0").commit();
            }else if(position == 1){
                getSupportFragmentManager().beginTransaction().replace(R.id.rel,fragment1, "position_1").commit();
            }

        });
        recyclerView.setAdapter(reAdapter);
    }

    private class CheckTypesTask extends AsyncTask<String,String,String>{

        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            asyncDialog.setMessage("로딩중입니다");

            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                for(int i =0; i<5; i++){
                    publishProgress("" + (int)(i*30));
                    Thread.sleep(250);
                }

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(String... progress){
            asyncDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            asyncDialog.dismiss();
            super.onPostExecute(s);
        }
    }



}
