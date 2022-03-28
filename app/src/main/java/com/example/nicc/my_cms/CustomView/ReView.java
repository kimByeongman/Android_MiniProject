package com.example.nicc.my_cms.CustomView;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nicc.my_cms.Implements.onClickLisImpl;
import com.example.nicc.my_cms.R;


/**
 * Created by NICC on 2022-03-17.
 */

public class ReView extends ConstraintLayout{

    private static final String TAG = "ReView";

    RecyclerView rec;
    ImageView imageView;
    Integer imageList;
    onClickLisImpl activityStart;

    public ReView(Context context) {
        super(context);
        initVIew(context);
    }

    public ReView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVIew(context);
    }

    public ReView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVIew(context);
    }

    private void  initVIew(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.recycler_image, this);
        rec = view.findViewById(R.id.rec);
        imageView = view.findViewById(R.id.imageview);

    }

    public  void setImage(Integer imageList){
        this.imageList = imageList;
        Glide.with(getContext())
                .load(imageList)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.image14)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public void setOnClick(Integer imageList , int position, final onClickLisImpl onClickLis){
        this.imageList = imageList;
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    onClickLis.onClickList(position);
                }else if(position == 1){
                    onClickLis.onClickList(position);
                }
            }
        });
    }

}
