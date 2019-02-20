package com.framgia.music_51.data.resource.sql;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.framgia.music_51.data.model.PublisherMetadata;
import com.framgia.music_51.data.model.Track;

import static com.framgia.music_51.data.resource.sql.TrackDataBase.DB_VERSION;

@Database(entities = {Track.class, PublisherMetadata.class}, version = DB_VERSION, exportSchema = false)
public abstract class TrackDataBase extends RoomDatabase {
    public static final int DB_VERSION = 1;
    private static TrackDataBase sInstance;
    private static final String DB_NAME = "track";

    public abstract TrackDao trackDao();

    public static TrackDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (TrackDataBase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            TrackDataBase.class,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }
}
