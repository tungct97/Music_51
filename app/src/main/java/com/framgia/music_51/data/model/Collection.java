
package com.framgia.music_51.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Collection {


    @SerializedName("collection")
    @Expose
    private List<Track> collection = null;
    @SerializedName("track")
    @Expose
    private Track mTrack;
    @SerializedName("score")
    @Expose
    private Integer mScore;

    public Track getTrack() {
        return mTrack;
    }

    public List<Track> getCollection() {
        return collection;
    }

    public void setCollection(List<Track> collection) {
        this.collection = collection;
    }

    public void setTrack(Track track) {
        mTrack = track;
    }

    public Integer getScore() {
        return mScore;
    }

    public void setScore(Integer score) {
        mScore = score;
    }
}
