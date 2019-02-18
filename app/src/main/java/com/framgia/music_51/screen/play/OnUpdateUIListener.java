package com.framgia.music_51.screen.play;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.Track;

public interface OnUpdateUIListener {

    void updateStateButton(boolean isPlaying);

    void onUpdateUiPlay(Track track);

    void onUpdateSeekbar();

    void onLikeStateChange(boolean like);

    void onShuffleStateChange(boolean shuffle);

    void onLoopStateChange(@LoopType int type);

}
