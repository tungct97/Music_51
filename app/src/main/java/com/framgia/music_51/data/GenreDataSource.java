package com.framgia.music_51.data;

import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;

import io.reactivex.Single;

public interface GenreDataSource {
    interface Remote {
        Single<MusicResponse> getGenre(String kind, @GenreType String type, int limit);
    }
}
