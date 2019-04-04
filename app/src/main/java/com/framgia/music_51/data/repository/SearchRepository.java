package com.framgia.music_51.data.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.framgia.music_51.data.SearchDataSource;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.Track;

import java.util.List;

import io.reactivex.Single;

public class SearchRepository implements SearchDataSource.Remote, SearchDataSource.Local {
    private static SearchRepository sInstance;
    private SearchDataSource.Remote mRemote;
    private SearchDataSource.Local mLocal;

    public SearchRepository(SearchDataSource.Remote remote, SearchDataSource.Local local) {
        mRemote = remote;
        mLocal = local;
    }

    public static SearchRepository getInstance(SearchDataSource.Remote remote, SearchDataSource.Local local) {
        if (sInstance == null) {
            synchronized (SearchRepository.class) {
                sInstance = new SearchRepository(remote, local);
            }
        }
        return sInstance;
    }

    @Override
    public void saveSearch(Track search) {
        mLocal.saveSearch(search);
    }

    @Override
    public LiveData<List<Track>> getHistory() {
        return mLocal.getHistory();
    }

    @Override
    public void removeHistory(Track search) {
        mLocal.removeHistory(search);
    }

    @Override
    public void deleteAllSearch() {
        Log.d("TAG1", "BÀo");
        mLocal.deleteAllSearch();
    }

    @Override
    public Single<Collection> getSearchTrack(String q) {
        return mRemote.getSearchTrack(q);
    }
}
