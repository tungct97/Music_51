package com.framgia.music_51.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublisherMetadata implements Parcelable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("urn")
    @Expose
    private String mUrn;
    @SerializedName("artist")
    @Expose
    private String mArtist;
    @SerializedName("contains_music")
    @Expose
    private boolean mContainsMusic;
    @SerializedName("publisher")
    @Expose
    private String mPublisher;
    @SerializedName("writer_composer")
    @Expose
    private String mWriterComposer;
    @SerializedName("album_title")
    @Expose
    private String mAlbumTitle;

    public static final Creator<PublisherMetadata> CREATOR = new Creator<PublisherMetadata>() {
        @Override
        public PublisherMetadata createFromParcel(Parcel in) {
            return new PublisherMetadata(in);
        }

        @Override
        public PublisherMetadata[] newArray(int size) {
            return new PublisherMetadata[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUrn() {
        return mUrn;
    }

    public void setUrn(String urn) {
        mUrn = urn;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public boolean isContainsMusic() {
        return mContainsMusic;
    }

    public void setContainsMusic(boolean containsMusic) {
        mContainsMusic = containsMusic;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getWriterComposer() {
        return mWriterComposer;
    }

    public void setWriterComposer(String writerComposer) {
        mWriterComposer = writerComposer;
    }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        mAlbumTitle = albumTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mUrn);
        parcel.writeString(mArtist);
        parcel.writeByte((byte) (mContainsMusic ? 1 : 0));
        parcel.writeString(mPublisher);
        parcel.writeString(mWriterComposer);
        parcel.writeString(mAlbumTitle);
    }

    protected PublisherMetadata(Parcel in) {
        mId = in.readInt();
        mUrn = in.readString();
        mArtist = in.readString();
        mContainsMusic = in.readByte() != 0;
        mPublisher = in.readString();
        mWriterComposer = in.readString();
        mAlbumTitle = in.readString();
    }
}
