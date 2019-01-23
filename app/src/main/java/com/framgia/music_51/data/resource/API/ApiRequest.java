package com.framgia.music_51.data.resource.API;

import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("/charts")
    Single<MusicResponse> getGenres(
            @Query("kind") String kind,
            @Query("genre") @GenreType String type);

    @GET("/charts")
    Single<MusicResponse> getTracks(
            @Query("kind") String kind,
            @Query("genre") @GenreType String type);
}
