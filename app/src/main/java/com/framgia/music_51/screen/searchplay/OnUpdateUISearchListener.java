package com.framgia.music_51.screen.searchplay;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;

public interface OnUpdateUISearchListener {

    void updateStateButton(boolean isPlaying);

    void onUpdateUiPlay(Search track);

    void onUpdateSeekbar();

    void onLikeStateChange(boolean like);

    void onShuffleStateChange(boolean shuffle);

    void onLoopStateChange(@LoopType int type);

}
