package com.framgia.music_51.screen.searchplay;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;

public interface MediaSearchListener {
    boolean isPlaying();

    void play();

    void next();

    void previous();

    void seek(int position);

    void loop(@LoopType int type);

    void shuffle(boolean isShuffle);

    int getCurrentPosition();

    Search getCurrentTrack();

    int getDuration();

    void download();

    void favouriteTrack(boolean like);
}
