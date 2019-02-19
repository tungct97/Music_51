package com.framgia.music_51.data.resource.API;

import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Search;

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
            @Query("genre") @GenreType String type,
            @Query("offset") int offset);

    @GET("/search/tracks")
    Single<Search> getSearch(@Query("q") String id);
}
