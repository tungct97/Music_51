package com.framgia.music_51.data.repository;

import com.framgia.music_51.data.TrackDataSource;
import com.framgia.music_51.data.model.MusicResponse;

import io.reactivex.Single;

public class TrackRepository implements TrackDataSource.Remote {
    private static TrackRepository sInstance;
    private TrackDataSource.Remote mRemote;

    private TrackRepository(TrackDataSource.Remote remote) {
        mRemote = remote;
    }

    public static TrackRepository getInstance(TrackDataSource.Remote remote) {
        if (sInstance == null) {
            synchronized (TrackRepository.class) {
                sInstance = new TrackRepository(remote);
            }
        }
        return sInstance;
    }

    @Override
    public Single<MusicResponse> getTracks(String kind, String type, int offset) {
        return mRemote.getTracks(kind, type, offset);
    }
}

