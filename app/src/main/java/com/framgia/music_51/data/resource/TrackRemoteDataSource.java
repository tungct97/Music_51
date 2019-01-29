package com.framgia.music_51.data.resource;

import com.framgia.music_51.data.TrackDataSource;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.resource.API.ApiRequest;
import com.framgia.music_51.data.resource.API.SoundCloudService;

import io.reactivex.Single;

public class TrackRemoteDataSource implements TrackDataSource.Remote {
    private static TrackRemoteDataSource sInstance;
    private ApiRequest mUtils;

    private TrackRemoteDataSource(ApiRequest utils) {
        mUtils = utils;
    }

    public static TrackRemoteDataSource getInstance() {
        if (sInstance == null) {
            synchronized (GenreRemoteDataSource.class) {
                sInstance = new TrackRemoteDataSource(SoundCloudService.getGenreService());
            }
        }
        return sInstance;
    }

    @Override
    public Single<MusicResponse> getTracks(String kind, String type, int offset) {
        return mUtils.getTracks(kind, type, offset);
    }
}
