package com.framgia.music_51.data.model;

public class PlayMode {
    private static PlayMode sInstance;
    private boolean mIsShuffle;
    private int mLoopMode;

    public PlayMode() {
    }

    public static PlayMode getInstance() {
        if (sInstance == null) {
            synchronized (PlayMode.class) {
                sInstance = new PlayMode();
            }
        }
        return sInstance;
    }

    public boolean isShuffer() {
        return mIsShuffle;
    }

    public void setShuffer(boolean shuffler) {
        mIsShuffle = shuffler;
    }

    public int getLoopMode() {
        return mLoopMode;
    }

    public void setLoopMode(@LoopType int loopMode) {
        mLoopMode = loopMode;
    }
}
