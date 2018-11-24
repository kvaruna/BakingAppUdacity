package com.example.varun.bakingappudacity.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@org.parceler.Parcel
public class Step implements Serializable {

    @SerializedName("id")
    @Expose
     Integer id;
    @SerializedName("shortDescription")
    @Expose
     String shortDescription;
    @SerializedName("description")
    @Expose
     String description;
    @SerializedName("videoURL")
    @Expose
     String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
     String thumbnailURL;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

}