package com.framgia.music_51.data;

import android.arch.lifecycle.LiveData;

import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;

import java.util.List;

import io.reactivex.Single;

public interface TrackDataSource {
    interface Remote {
        Single<MusicResponse> getTracks(String kind, @GenreType String type, int offset);
    }


    interface Local {
        void addFavorite(Track track);

        LiveData<List<Track>> getFavorites();

        void removeFavorite(Track track);

        void addDownload(Track track);

        LiveData<List<Track>> getDownload();
    }
}
