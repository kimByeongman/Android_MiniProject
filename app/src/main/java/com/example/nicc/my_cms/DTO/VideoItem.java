package com.example.nicc.my_cms.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NICC on 2022-03-17.
 */

public class VideoItem {

    @Expose
    @SerializedName("title") private String title;

    @Expose
    @SerializedName("subTitle") private String subTitle;

    @Expose
    @SerializedName("desc") private String desc;

    @Expose
    @SerializedName("videoUrl") private String videoUrl;

    @Expose
    @SerializedName("thumb") private String thumb;

    public VideoItem() {}

    public VideoItem(String title, String subTitle, String desc, String videoUrl, String thumb) {
        this.title = title;
        this.subTitle = subTitle;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
