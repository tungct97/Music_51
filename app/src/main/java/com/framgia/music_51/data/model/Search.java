package com.framgia.music_51.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
public class Search implements Parcelable {

    private String mArtworkUrl;
    private Integer mDuration;
    private String mGenre;
    @PrimaryKey(autoGenerate = true)
    private Integer mId;
    private String mKind;
    private String mLabelName;
    private String mLastModified;
    private String mTagList;
    private String mTitle;
    private String mUri;
    private String mUrn;
    private Integer mUserId;

    public Search(JSONObject jsonObject) throws JSONException {
        mArtworkUrl = jsonObject.getString(JSONKey.ARTWORK_URL);
        mDuration = jsonObject.getInt(JSONKey.DURATION);
        mGenre = jsonObject.getString(JSONKey.GENRE);
        mId = jsonObject.getInt(JSONKey.ID);
        mKind = jsonObject.getString(JSONKey.KIND);
        mLabelName = jsonObject.getString(JSONKey.LABLE_NAME);
        mLastModified = jsonObject.getString(JSONKey.LAST_MODIFIED);
        mTitle = jsonObject.getString(JSONKey.TITLE);
        mUri = jsonObject.getString(JSONKey.URI);
        mUrn = jsonObject.getString(JSONKey.URN);
        mUserId = jsonObject.getInt(JSONKey.USER_ID);
        mTagList = jsonObject.getString(JSONKey.TAG_LIST);
    }

    public Search() {
    }

    protected Search(Parcel in) {
        mArtworkUrl = in.readString();
        if (in.readByte() == 0) {
            mDuration = null;
        } else {
            mDuration = in.readInt();
        }
        mGenre = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mKind = in.readString();
        mLabelName = in.readString();
        mLastModified = in.readString();
        mTagList = in.readString();
        mTitle = in.readString();
        mUri = in.readString();
        mUrn = in.readString();
        if (in.readByte() == 0) {
            mUserId = null;
        } else {
            mUserId = in.readInt();
        }
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

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public Integer getDuration() {
        return mDuration;
    }

    public void setDuration(Integer duration) {
        mDuration = duration;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getLabelName() {
        return mLabelName;
    }

    public void setLabelName(String labelName) {
        mLabelName = labelName;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public String getTagList() {
        return mTagList;
    }

    public void setTagList(String tagList) {
        mTagList = tagList;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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

    public Integer getUserId() {
        return mUserId;
    }

    public void setUserId(Integer userId) {
        mUserId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        if (mDuration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mDuration);
        }
        dest.writeString(mGenre);
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mId);
        }
        dest.writeString(mKind);
        dest.writeString(mLabelName);
        dest.writeString(mLastModified);
        dest.writeString(mTagList);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeString(mUrn);
        if (mUserId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mUserId);
        }
    }

    public static class JSONKey {
        public static final String COLLECTION = "collection";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DURATION = "duration";
        public static final String GENRE = "genre";
        public static final String ID = "id";
        public static final String KIND = "kind";
        public static final String LABLE_NAME = "label_name";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String TAG_LIST = "tag_list";
        public static final String TITLE = "title";
        public static final String URI = "uri";
        public static final String URN = "urn";
        public static final String USER_ID = "user_id";
    }
}