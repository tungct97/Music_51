package com.framgia.music_51.screen.play;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.Track;

import java.util.List;

public interface MediaListener {
    boolean isPlaying();

    void play();

    void next();

    void previous();

    void seek(int position);

    void loop(@LoopType int type);

    void shuffle(boolean isShuffle);

    int getCurrentPosition();

    Track getCurrentTrack();

    int possotion();

    List<Track> getListTrack();

    int getDuration();

    void download();

    void favouriteTrack(boolean like);

    interface OnChangePlayNow {
        void onTrackChange(Track track, int position);

        void onListChange(List<Track> tracks);
    }

    interface OnChangeButtonMediaPlayer {
        void onTrackChange(Track track);

        void onFavoritesChange(int state);

        void onDownLoadChange(int state);
    }

    interface OnMiniPlayerChangeListener {
        void onMediaStateChange(boolean isPlaying);

        void onTrackChange(Track track);
    }
}
