package com.framgia.music_51.data.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.framgia.music_51.data.TrackDataSource;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;

import java.util.List;

import io.reactivex.Single;

public class TrackRepository implements TrackDataSource.Remote, TrackDataSource.Local {
    private static TrackRepository sInstance;
    private TrackDataSource.Remote mRemote;
    private TrackDataSource.Local mLocal;

    private TrackRepository(TrackDataSource.Remote remote, TrackDataSource.Local local) {
        mRemote = remote;
        mLocal = local;
    }

    public static TrackRepository getInstance(TrackDataSource.Remote remote, TrackDataSource.Local local) {
        if (sInstance == null) {
            synchronized (TrackRepository.class) {
                sInstance = new TrackRepository(remote, local);
            }
        }
        return sInstance;
    }

    @Override
    public Single<MusicResponse> getTracks(String kind, String type, int offset) {
        return mRemote.getTracks(kind, type, offset);
    }

    @Override
    public void addFavorite(Track track) {
        mLocal.addFavorite(track);
    }

    @Override
    public LiveData<List<Track>> getFavorites() {
        return mLocal.getFavorites();
    }

    @Override
    public void removeFavorite(Track track) {
        mLocal.removeFavorite(track);
    }

    @Override
    public void addDownload(Track track) {
        mLocal.addDownload(track);
    }

    @Override
    public LiveData<List<Track>> getDownload() {
        return mLocal.getDownload();
    }
}

