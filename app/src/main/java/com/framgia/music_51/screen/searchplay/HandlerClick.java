package com.framgia.music_51.screen.searchplay;

import android.content.Context;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.play.MediaListener;
import com.framgia.music_51.screen.play.PlayerViewModel;
import com.framgia.music_51.screen.service.SearchService;
import com.framgia.music_51.screen.service.TrackService;

public class HandlerClick {
    private MediaSearchListener mMediaListener;
    private PlayMode mPlayMode;
    private PlaySearchViewModel mViewModel;
    private SearchService mService;
    private Context mContext;

    public HandlerClick(MediaSearchListener mediaListener) {
        mMediaListener = mediaListener;
    }

    public HandlerClick(Context context, MediaSearchListener mediaListener, PlayMode playMode, PlaySearchViewModel viewModel,
                        SearchService service) {
        mMediaListener = mediaListener;
        mPlayMode = playMode;
        mViewModel = viewModel;
        mService = service;
        mContext = context;
        iniPlayMode();
    }

    public void onPlayClick() {
        mMediaListener.play();
    }

    public void onNextClick() {
        mMediaListener.next();
    }

    public void onPreviousClick() {
        mMediaListener.previous();
    }

    public void onClickShuffle(boolean shuffle) {
        mService.shuffle(mPlayMode.isShuffer());
        mPlayMode.setShuffer(mPlayMode.isShuffer());
        mViewModel.savePlayerMode(mPlayMode);
    }

    public void onLoopClick() {
        mPlayMode = mViewModel.getPlayMode();
        switch (mPlayMode.getLoopMode()) {
            case LoopType.NO_LOOP:
                mService.loop(LoopType.LOOP_ALL);
                break;
            case LoopType.LOOP_ONE:
                mService.loop(LoopType.NO_LOOP);
                break;
            case LoopType.LOOP_ALL:
                mService.loop(LoopType.LOOP_ONE);
                break;
        }
    }

    public void iniPlayMode() {
        switch (mPlayMode.getLoopMode()) {
            case LoopType.NO_LOOP:
                mViewModel.setLoopType(LoopType.NO_LOOP);
                break;
            case LoopType.LOOP_ONE:
                mViewModel.setLoopType(LoopType.LOOP_ONE);
                break;
            case LoopType.LOOP_ALL:
                mViewModel.setLoopType(LoopType.LOOP_ALL);
                break;
        }
    }

//    public void isFavorite(Track track, boolean like) {
//        mViewModel.isFavourite(track, !like);
//        mMediaListener.favouriteTrack(!like);
//    }
//
//    public void onDownloadClick(Track track) {
//        mService.download();
//        mViewModel.saveData(track);
//    }
}
