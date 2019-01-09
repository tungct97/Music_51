package com.framgia.music_51.data.model;

import android.support.annotation.StringDef;

import static com.framgia.music_51.data.model.GenreType.ALL_AUDIO;
import static com.framgia.music_51.data.model.GenreType.ALL_MUSIC;
import static com.framgia.music_51.data.model.GenreType.ALTERNATIVEROCK;
import static com.framgia.music_51.data.model.GenreType.AMBIENT;
import static com.framgia.music_51.data.model.GenreType.CLASSICAL;
import static com.framgia.music_51.data.model.GenreType.COUNTRY;

@StringDef({ALL_MUSIC, ALL_AUDIO, ALTERNATIVEROCK, CLASSICAL, AMBIENT, COUNTRY})
public @interface GenreType {
    String ALL_MUSIC = "soundcloud:genres:all-music";
    String ALL_AUDIO = "soundcloud:genres:all-audio";
    String ALTERNATIVEROCK = "soundcloud:genres:alternativerock";
    String AMBIENT = "soundcloud:genres:ambient";
    String CLASSICAL = "soundcloud:genres:classical";
    String COUNTRY = "soundcloud:genres:country";
}
