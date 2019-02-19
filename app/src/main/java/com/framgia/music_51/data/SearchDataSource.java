package com.framgia.music_51.data;

import android.arch.lifecycle.LiveData;

import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;

import java.util.List;

public interface SearchDataSource {
    interface Remote {
        void getSearch(int limit, Callback<List<Search>> callback);
    }

    interface Local {
        void saveSearch(Track track);

        LiveData<List<Track>> getHistory();
    }
}
