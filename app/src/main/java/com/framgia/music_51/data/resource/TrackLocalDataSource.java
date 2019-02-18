package com.framgia.music_51.data.resource;

import android.arch.lifecycle.LiveData;

import com.framgia.music_51.data.TrackDataSource;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.resource.sql.TrackDao;

import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.Local {
    private static TrackLocalDataSource sInstance;
    private TrackDao mDao;

    private TrackLocalDataSource(TrackDao dao) {
        mDao = dao;
    }

    public static TrackLocalDataSource getInstance(TrackDao noteDao) {
        if (sInstance == null) {
            synchronized (TrackLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new TrackLocalDataSource(noteDao);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void addFavorite(Track track) {
        mDao.addFavorite(track);
    }

    @Override
    public LiveData<List<Track>> getFavorites() {
        return mDao.getFavorites();
    }

    @Override
    public void removeFavorite(Track track) {
        mDao.removeFavorite(track);
    }
}
