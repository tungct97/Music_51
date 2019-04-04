package com.framgia.music_51.data.resource.API;

import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("/charts")
    Single<MusicResponse> getGenres(
            @Query("kind") String kind,
            @Query("genre") @GenreType String type,
            @Query("limit") int limit);

    @GET("/charts")
    Single<MusicResponse> getTracks(
            @Query("kind") String kind,
            @Query("genre") @GenreType String type,
            @Query("offset") int offset);

    @GET("/search/tracks?sc_a_id=e59d651b-5b9e-4eec-8e2a-0f98c3eb0567&variant_ids=&facet=genre&user_id=909695-112147-772262-91428&limit=20&offset=0&linked_partitioning=1&app_version=1532422103&app_locale=en")
    Single<Collection> getSearch(@Query("q") String q);
}
