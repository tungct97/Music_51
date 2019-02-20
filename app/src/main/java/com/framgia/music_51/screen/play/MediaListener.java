package com.framgia.music_51.screen.play;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.Track;

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

    int getDuration();

    void download();

    void favouriteTrack(boolean like);
}
