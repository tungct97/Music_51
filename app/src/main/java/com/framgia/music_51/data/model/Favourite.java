package com.framgia.music_51.data.model;

import android.arch.persistence.room.PrimaryKey;

public class Favourite {
    @PrimaryKey
    protected boolean isFavourite;

    public Favourite() {

    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
