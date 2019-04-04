package com.framgia.music_51.data.repository;

import com.framgia.music_51.data.GenreDataSource;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;

import io.reactivex.Single;

public class GenreRepository implements GenreDataSource.Remote {
    private static GenreRepository sInstance;
    private GenreDataSource.Remote mRemote;

    private GenreRepository(GenreDataSource.Remote remote) {
        mRemote = remote;
    }

    public static GenreRepository getInstance(GenreDataSource.Remote remote) {
        if (sInstance == null) {
            synchronized (GenreRepository.class) {
                sInstance = new GenreRepository(remote);
            }
        }
        return sInstance;
    }

    @Override
    public Single<MusicResponse> getGenre(String kind, String type, int limit) {
        return mRemote.getGenre(kind, type, limit);
    }
}
