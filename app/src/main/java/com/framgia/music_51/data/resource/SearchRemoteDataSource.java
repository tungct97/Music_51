package com.framgia.music_51.data.resource;

import com.framgia.music_51.BuildConfig;
import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.SearchDataSource;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.resource.API.search.SearchAsysTask;
import com.framgia.music_51.screen.Utils;

import java.util.List;

public class SearchRemoteDataSource implements SearchDataSource.Remote {
    private static SearchRemoteDataSource sInstance;

    private SearchRemoteDataSource() {
    }

    public static SearchRemoteDataSource getInstance() {
        if (sInstance == null) {
            synchronized (SearchRemoteDataSource.class) {
                sInstance = new SearchRemoteDataSource();
            }
        }
        return sInstance;
    }

    private void getTrackBySearch(int limit, Callback callBack) {
        new SearchAsysTask(callBack).execute(Utils.URL);
    }


    @Override
    public void getSearch(int limit, Callback<List<Search>> callback) {
        getTrackBySearch(limit, callback);
    }
}
