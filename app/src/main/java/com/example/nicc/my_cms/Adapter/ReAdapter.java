package com.example.nicc.my_cms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicc.my_cms.CustomView.ReView;
import com.example.nicc.my_cms.Implements.ActivityStartImpl;

import java.util.List;


/**
 * Created by NICC on 2022-03-17.
 */

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ViewHolder>{
    List<Integer> imageList;
    Context context;
    static final String TAG = "I WANT TO GO";
    ActivityStartImpl activityStart;

    public ReAdapter() {}

    public void setImage(List<Integer> imageList, Context context, ActivityStartImpl activityStart){
        this.imageList = imageList;
        this.context = context;
        this.activityStart = activityStart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReView reView = new ReView(parent.getContext());
        return new ViewHolder(reView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.getReView().setImage(imageList.get(position));
            holder.getReView().setOnClick(imageList.get(position),position, activityStart);

    }
    @Override
    public int getItemCount() {
        return imageList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ReView reView;
        public ViewHolder(View itemView) {
            super(itemView);
            reView = (ReView) itemView;
        }
        public ReView getReView(){return reView;}
    }

}
