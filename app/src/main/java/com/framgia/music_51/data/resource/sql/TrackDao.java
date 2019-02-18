package com.framgia.music_51.data.resource.sql;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.framgia.music_51.data.model.Track;

import java.util.List;

@Dao
public interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavorite(Track track);

    @Query("SELECT * FROM track")
    LiveData<List<Track>> getFavorites();

    @Delete
    void removeFavorite(Track track);
}
