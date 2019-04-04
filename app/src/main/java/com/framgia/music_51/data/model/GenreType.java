package com.framgia.music_51.data.model;

import android.support.annotation.StringDef;

import static com.framgia.music_51.data.model.GenreType.ALL_AUDIO;
import static com.framgia.music_51.data.model.GenreType.ALL_MUSIC;
import static com.framgia.music_51.data.model.GenreType.ALTERNATIVEROCK;
import static com.framgia.music_51.data.model.GenreType.AMBIENT;
import static com.framgia.music_51.data.model.GenreType.CLASSICAL;
import static com.framgia.music_51.data.model.GenreType.COUNTRY;
import static com.framgia.music_51.data.model.GenreType.DANCEEDM;
import static com.framgia.music_51.data.model.GenreType.DANCEHALL;
import static com.framgia.music_51.data.model.GenreType.DEEPHOUSE;
import static com.framgia.music_51.data.model.GenreType.DISCO;
import static com.framgia.music_51.data.model.GenreType.DRUMBASS;
import static com.framgia.music_51.data.model.GenreType.ELECTRONIC;

@StringDef({ALL_MUSIC, ALL_AUDIO, ALTERNATIVEROCK, CLASSICAL, AMBIENT, COUNTRY, DANCEEDM,
        DRUMBASS, DISCO, DANCEHALL, DEEPHOUSE, ELECTRONIC})
public @interface GenreType {
    String ALL_MUSIC = "soundcloud:genres:all-music";
    String ALL_AUDIO = "soundcloud:genres:all-audio";
    String ALTERNATIVEROCK = "soundcloud:genres:alternativerock";
    String AMBIENT = "soundcloud:genres:ambient";
    String CLASSICAL = "soundcloud:genres:classical";
    String COUNTRY = "soundcloud:genres:country";
    String DANCEEDM = "soundcloud:genres:danceedm";
    String DANCEHALL = "soundcloud:genres:dancehall";
    String DEEPHOUSE = "soundcloud:genres:deephouse";
    String DISCO = "soundcloud:genres:disco";
    String DRUMBASS = "soundcloud:genres:drumbass";
    String ELECTRONIC = "soundcloud:genres:electronic";
//        <item>soundcloud:genres:drumbass</item>
//        <item>soundcloud:genres:dubstep</item>
//        <item>soundcloud:genres:electronic</item>
//        <item>soundcloud:genres:folksingersongwriter</item>
}
