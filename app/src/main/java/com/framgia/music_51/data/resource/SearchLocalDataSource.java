package com.framgia.music_51.data.resource;

import android.arch.lifecycle.LiveData;

import com.framgia.music_51.data.SearchDataSource;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.resource.sql.TrackDao;

import java.util.List;

public class SearchLocalDataSource implements SearchDataSource.Local {
    private static SearchLocalDataSource sInstance;
    private TrackDao mDao;

    private SearchLocalDataSource(TrackDao trackDao) {
        mDao = trackDao;
    }

    public static SearchLocalDataSource getInstance(TrackDao trackDao) {
        if (sInstance == null) {
            synchronized (SearchLocalDataSource.class) {
                sInstance = new SearchLocalDataSource(trackDao);
            }
        }
        return sInstance;
    }

    @Override
    public void saveSearch(Track search) {
        mDao.addHistory(search);
    }

    @Override
    public LiveData<List<Track>> getHistory() {
      return mDao.getHistory();
    }

    @Override
    public void removeHistory(Track search) {
        mDao.removeHistory(search);
    }

    @Override
    public void deleteAllSearch() {
        mDao.deleteAllSearch();
    }
}
