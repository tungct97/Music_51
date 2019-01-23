package com.framgia.music_51.data;

import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;

import io.reactivex.Single;

public interface TrackDataSource {
    interface Remote {
        Single<MusicResponse> getTracks(String kind, @GenreType String type);
    }
}
