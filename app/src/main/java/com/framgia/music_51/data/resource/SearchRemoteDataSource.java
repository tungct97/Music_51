package com.framgia.music_51.data.resource;

import com.framgia.music_51.data.SearchDataSource;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.resource.API.ApiRequest;
import com.framgia.music_51.data.resource.API.SoundCloudService;

import io.reactivex.Single;

public class SearchRemoteDataSource implements SearchDataSource.Remote {
    private static SearchRemoteDataSource sInstance;
    private ApiRequest mUtils;

    private SearchRemoteDataSource(ApiRequest utils) {
        mUtils = utils;
    }

    public static SearchRemoteDataSource getInstance() {
        if (sInstance == null) {
            synchronized (SearchRemoteDataSource.class) {
                sInstance = new SearchRemoteDataSource(SoundCloudService.getGenreService());
            }
        }
        return sInstance;
    }

    @Override
    public Single<Collection> getSearchTrack(String q) {
        return mUtils.getSearch(q);
    }
}
