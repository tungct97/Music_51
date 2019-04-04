package com.framgia.music_51.data;

import android.arch.lifecycle.LiveData;

import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;

import java.util.List;

import io.reactivex.Single;

public interface SearchDataSource {
    interface Remote {
        Single<Collection> getSearchTrack(String q);
    }

    interface Local {
        void saveSearch(Track search);

        LiveData<List<Track>> getHistory();

        void removeHistory(Track search);

        void deleteAllSearch();
    }
}
