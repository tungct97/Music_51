package com.framgia.music_51.screen.play;

import android.content.Context;

import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.service.TrackService;

public class HandlerClick {
    private MediaListener mMediaListener;
    private PlayMode mPlayMode;
    private PlayerViewModel mViewModel;
    private TrackService mService;
    private Context mContext;

    public HandlerClick(MediaListener mediaListener) {
        mMediaListener = mediaListener;
    }

    public HandlerClick(Context context, MediaListener mediaListener, PlayMode playMode, PlayerViewModel viewModel,
                        TrackService service) {
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

    public void isFavorite(Track track, boolean like) {
        mViewModel.isFavourite(track, !like);
        mMediaListener.favouriteTrack(!like);
    }

    public void onDownloadClick(Track track) {
        mService.download();
        mViewModel.saveData(track);
    }
}
