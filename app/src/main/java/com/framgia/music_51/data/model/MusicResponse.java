
package com.framgia.music_51.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicResponse implements Parcelable {

    @SerializedName("genre")
    @Expose
    private String mGenre;
    @SerializedName("kind")
    @Expose
    private String mKind;
    @SerializedName("last_updated")
    @Expose
    private String mLastUpdated;
    @SerializedName("collection")
    @Expose
    private List<Collection> mCollections = null;
    @SerializedName("query_urn")
    @Expose
    private String mQueryUrn;
    @SerializedName("next_href")
    @Expose
    private String mNextHref;

    public List getTracks() {
        List<Track> tracks = new ArrayList<>();
        for (Collection collection : this.getCollections()) {
            tracks.add(collection.getTrack());
        }
        return tracks;
    }

    public static final Creator<MusicResponse> CREATOR = new Creator<MusicResponse>() {
        @Override
        public MusicResponse createFromParcel(Parcel in) {
            return new MusicResponse(in);
        }

        @Override
        public MusicResponse[] newArray(int size) {
            return new MusicResponse[size];
        }
    };

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public List<Collection> getCollections() {
        return mCollections;
    }

    public void setCollections(List<Collection> collections) {
        mCollections = collections;
    }

    public String getQueryUrn() {
        return mQueryUrn;
    }

    public void setQueryUrn(String queryUrn) {
        mQueryUrn = queryUrn;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mGenre);
        dest.writeString(mKind);
        dest.writeString(mLastUpdated);
        dest.writeString(mQueryUrn);
        dest.writeString(mNextHref);
    }

    protected MusicResponse(Parcel in) {
        mGenre = in.readString();
        mKind = in.readString();
        mLastUpdated = in.readString();
        mQueryUrn = in.readString();
        mNextHref = in.readString();
    }
}
