package com.framgia.music_51.data.resource;

import android.content.Context;

import com.framgia.music_51.data.PlayModeDataSouce;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.resource.shareprefs.SharePrefsImpl;

public class PlayModeLocalDataSource implements PlayModeDataSouce {
    private static final String PREF_IS_SHUFFLER = "PREF_IS_SHUFFLER";
    private static final String PREF_PLAY_MODE = "PREF_PLAY_MODE";
    private static PlayModeLocalDataSource sInstance;
    private SharePrefsImpl mSharePrefs;

    private PlayModeLocalDataSource(Context context) {
        mSharePrefs = SharePrefsImpl.getInstance(context);
    }

    public static PlayModeLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PlayModeLocalDataSource.class) {
                sInstance = new PlayModeLocalDataSource(context);
            }
        }
        return sInstance;
    }

    @Override
    public void savePlayMode(PlayMode playMode) {
        if (playMode == null) {
            return;
        }
        mSharePrefs.put(PREF_IS_SHUFFLER, playMode.isShuffer());
        mSharePrefs.put(PREF_PLAY_MODE, playMode.getLoopMode());
    }

    @Override
    public PlayMode getPlayMode() {
        PlayMode playMode = new PlayMode();
        playMode.setLoopMode(mSharePrefs.get(PREF_PLAY_MODE, Integer.class));
        playMode.setShuffer(mSharePrefs.get(PREF_IS_SHUFFLER, Boolean.class));
        return playMode;
    }
}
