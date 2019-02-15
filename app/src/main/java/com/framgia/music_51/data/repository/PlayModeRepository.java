package com.framgia.music_51.data.repository;

import com.framgia.music_51.data.PlayModeDataSouce;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.resource.PlayModeLocalDataSource;

public class PlayModeRepository implements PlayModeDataSouce {
    private static PlayModeRepository sInstance;
    private PlayModeLocalDataSource mDataSouce;

    private PlayModeRepository(PlayModeLocalDataSource dataSouce) {
        mDataSouce = dataSouce;
    }

    public static PlayModeRepository getInstance(PlayModeLocalDataSource dataSouce) {
        if (sInstance == null) {
            synchronized (PlayModeLocalDataSource.class) {
                sInstance = new PlayModeRepository(dataSouce);
            }
        }
        return sInstance;
    }

    @Override
    public void savePlayMode(PlayMode playMode) {
        mDataSouce.savePlayMode(playMode);
    }

    @Override
    public PlayMode getPlayMode() {
        return mDataSouce.getPlayMode();
    }
}
