
package com.framgia.music_51.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Track implements Parcelable {

    @SerializedName("artwork_url")
    @Expose
    private String mArtworkUrl;
    @SerializedName("downloadable")
    @Expose
    private boolean mDownloadable;
    @SerializedName("download_url")
    @Expose
    private String mDownloadUrl;
    @SerializedName("duration")
    @Expose
    private int mDuration;
    @SerializedName("genre")
    @Expose
    private String mGenre;
    @SerializedName("publisher_metadata")
    @Expose
    private PublisherMetadata mPublisherMetadata;
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("uri")
    @Expose
    private String mUri;
    @SerializedName("urn")
    @Expose
    private String mUrn;

    public Track(Parcel in) {
        mArtworkUrl = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadUrl = in.readString();
        mDuration = in.readInt();
        mGenre = in.readString();
        mId = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUrn = in.readString();
        mPublisherMetadata = in.readParcelable(PublisherMetadata.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeString(mDownloadUrl);
        dest.writeInt(mDuration);
        dest.writeString(mGenre);
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeString(mUrn);
        dest.writeParcelable((Parcelable) mPublisherMetadata, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public PublisherMetadata getPublisherMetadata() {
        return mPublisherMetadata;
    }

    public void setPublisherMetadata(PublisherMetadata publisherMetadata) {
        mPublisherMetadata = publisherMetadata;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getUrn() {
        return mUrn;
    }

    public void setUrn(String urn) {
        mUrn = urn;
    }

}
