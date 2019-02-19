package com.framgia.music_51.data.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.SearchDataSource;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.resource.SearchLocalDataSource;

import java.util.List;

public class SearchRepository implements SearchDataSource.Remote {
    private static SearchRepository sInstance;
    private SearchDataSource.Remote mRemote;

    public SearchRepository(SearchDataSource.Remote remote) {
        mRemote = remote;
    }

    public static SearchRepository getInstance(SearchDataSource.Remote remote) {
        if (sInstance == null) {
            synchronized (SearchRepository.class) {
                sInstance = new SearchRepository(remote);
            }
        }
        return sInstance;
    }

    @Override
    public void getSearch(int limit, Callback<List<Search>> callback) {
        mRemote.getSearch(limit, callback);
    }
}
