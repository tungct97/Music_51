package com.framgia.music_51.screen.search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.repository.SearchRepository;
import com.framgia.music_51.data.resource.SearchRemoteDataSource;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private SearchRepository mRepository;
    private MutableLiveData<List<Search>> mData = new MutableLiveData<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mRepository = SearchRepository.getInstance(SearchRemoteDataSource.getInstance());
    }

    LiveData<List<Search>> getQuery(int limit) {
        mRepository.getSearch(limit, new Callback<List<Search>>() {
            @Override
            public void getDataSuccess(List<Search> searches) {
                mData.setValue(searches);
            }

            @Override
            public void getDataFail(Exception e) {

            }
        });
        return mData;
    }
}
