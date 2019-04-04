package com.framgia.music_51.data.resource;

import com.framgia.music_51.data.GenreDataSource;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.resource.API.ApiRequest;
import com.framgia.music_51.data.resource.API.SoundCloudService;

import io.reactivex.Single;

public class GenreRemoteDataSource implements GenreDataSource.Remote {
    private static GenreRemoteDataSource sInstance;
    private ApiRequest mUtils;

    private GenreRemoteDataSource(ApiRequest utils) {
        mUtils = utils;
    }

    public static GenreRemoteDataSource getInstance() {
        if (sInstance == null) {
            synchronized (GenreRemoteDataSource.class) {
                sInstance = new GenreRemoteDataSource(SoundCloudService.getGenreService());
            }
        }
        return sInstance;
    }

    @Override
    public Single<MusicResponse> getGenre(String kind, String type, int limit) {
        return mUtils.getGenres(kind, type, limit);
    }
}
