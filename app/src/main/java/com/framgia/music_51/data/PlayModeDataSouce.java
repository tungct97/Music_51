package com.framgia.music_51.data;

import com.framgia.music_51.data.model.PlayMode;

public interface PlayModeDataSouce {
    void savePlayMode(PlayMode playMode);

    PlayMode getPlayMode();
}
