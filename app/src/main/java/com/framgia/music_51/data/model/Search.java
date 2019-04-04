package com.framgia.music_51.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "search")
public class Search implements Parcelable {

    @PrimaryKey
    private int mId;
    private int mDuration;
    private String mTitle;
    private String mArtworkUrl;
    private String mGenre;
    private String mDownloadURL;
    private String mArtist;
    private boolean mDownloadable;
    private boolean mDownloaded;
    private int mTypeTrack;

    public Search() {
    }

    public Search(JSONObject jsonObject) throws JSONException {
        this.mId = jsonObject.getInt(TrackJSON.ID);
        this.mTitle = jsonObject.getString(TrackJSON.TITLE);
        this.mArtworkUrl = jsonObject.getString(TrackJSON.ARTWORK_URL);
        this.mDuration = jsonObject.getInt(TrackJSON.DURATION);
        this.mGenre = jsonObject.getString(TrackJSON.GENRE);
        this.mDownloadable = jsonObject.getBoolean(TrackJSON.DOWNLOADABLE);
        this.mDownloadURL = jsonObject.getString(TrackJSON.DOWNLOAD_URL);
        this.mArtist = jsonObject.getJSONObject(TrackJSON.USER)
                .getString(TrackJSON.USER_NAME);
    }

    private Search(TrackBuilder trackBuilder) {
        this.mId = trackBuilder.mId;
        this.mTitle = trackBuilder.mTitle;
        this.mArtworkUrl = trackBuilder.mArtworkUrl;
        this.mDuration = trackBuilder.mDuration;
        this.mGenre = trackBuilder.mGenre;
        this.mDownloadable = trackBuilder.mDownloadable;
        this.mDownloaded = trackBuilder.mDownloaded;
        this.mDownloadURL = trackBuilder.mDownloadURL;
        this.mArtist = trackBuilder.mArtist;
        this.mTypeTrack = trackBuilder.mTypeTrack;
    }

    protected Search(Parcel in) {
        mId = in.readInt();
        mDuration = in.readInt();
        mTitle = in.readString();
        mArtworkUrl = in.readString();
        mGenre = in.readString();
        mDownloadURL = in.readString();
        mArtist = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloaded = in.readByte() != 0;
        mTypeTrack = in.readInt();
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mDuration);
        dest.writeString(mTitle);
        dest.writeString(mArtworkUrl);
        dest.writeString(mGenre);
        dest.writeString(mDownloadURL);
        dest.writeString(mArtist);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeByte((byte) (mDownloaded ? 1 : 0));
        dest.writeInt(mTypeTrack);
    }


    public static class TrackBuilder {
        private int mId;
        private String mTitle;
        private String mArtworkUrl;
        private int mDuration;
        private String mGenre;
        private boolean mDownloadable;
        private boolean mDownloaded;
        private String mDownloadURL;
        private String mArtist;
        private int mTypeTrack;

        public TrackBuilder setId(int id) {
            mId = id;
            return this;
        }

        public TrackBuilder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public TrackBuilder setArtworkUrl(String artworkUrl) {
            mArtworkUrl = artworkUrl;
            return this;
        }

        public TrackBuilder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        public TrackBuilder setGenre(String genre) {
            mGenre = genre;
            return this;
        }

        public TrackBuilder setDownloadable(boolean downloadable) {
            mDownloadable = downloadable;
            return this;
        }

        public TrackBuilder setDownloadURL(String downloadURL) {
            mDownloadURL = downloadURL;
            return this;
        }

        public TrackBuilder setArtist(String artist) {
            mArtist = artist;
            return this;
        }

        public TrackBuilder setDownloaded(boolean downloaded) {
            mDownloaded = downloaded;
            return this;
        }

        public TrackBuilder setTypeTrack(int typeTrack) {
            mTypeTrack = typeTrack;
            return this;
        }

        public Search build() {
            return new Search(this);
        }
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

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
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

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public String getDownloadURL() {
        return mDownloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        mDownloadURL = downloadURL;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public boolean isDownloaded() {
        return mDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        mDownloaded = downloaded;
    }

    public int getTypeTrack() {
        return mTypeTrack;
    }

    public void setTypeTrack(int typeTrack) {
        mTypeTrack = typeTrack;
    }

    public class TrackJSON {
        private static final String ID = "id";
        private static final String TITLE = "title";
        private static final String URI = "url";
        private static final String ARTWORK_URL = "artwork_url";
        private static final String DURATION = "duration";
        private static final String GENRE = "genre";
        private static final String DOWNLOADABLE = "downloadable";
        private static final String DOWNLOAD_URL = "download_url";
        private static final String USER_NAME = "username";
        private static final String USER = "user";
        public static final String COLLECTION = "collection";
        public static final String TRACK = "track";
    }

}
