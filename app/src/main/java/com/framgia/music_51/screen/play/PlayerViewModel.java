package com.framgia.music_51.screen.play;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.repository.PlayModeRepository;
import com.framgia.music_51.data.resource.PlayModeLocalDataSource;

public class PlayerViewModel extends AndroidViewModel {
    public ObservableField<Integer> positionSeekBar = new ObservableField<>();
    public ObservableField<Integer> maxSeekBar = new ObservableField<>();
    public ObservableField<Boolean> isShuffle = new ObservableField<>(false);
    public ObservableField<Integer> typeLoop = new ObservableField<>();
    public ObservableField<Boolean> playState = new ObservableField<>();
    public ObservableField<Integer> loopRes = new ObservableField<>();
    private MutableLiveData<Track> mCurrentTrack;
    private PlayModeRepository mRepository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        mRepository = PlayModeRepository.getInstance(PlayModeLocalDataSource.getInstance(application));
    }

    public MutableLiveData<Track> getCurrentTrack() {
        if (mCurrentTrack == null) {
            mCurrentTrack = new MutableLiveData<>();
        }
        return mCurrentTrack;
    }

    public void setData(Track track) {
        if (track == null) {
            return;
        }
        getCurrentTrack().setValue(track);
    }

    public PlayMode getPlayMode() {
        return mRepository.getPlayMode();
    }

    public void savePlayerMode(PlayMode playerMode) {
        mRepository.savePlayMode(playerMode);
    }

    public void setPlayState(boolean state) {
        playState.set(state);
    }

    public void setMaxSeekBar(int max) {
        maxSeekBar.set(max);
    }

    public void setShuffle(boolean shuffle) {
        isShuffle.set(shuffle);
    }

    public void onSeekBarPositionChange(int position) {
        positionSeekBar.set(position);
    }


    public void setLoopRes(@DrawableRes int res) {
        loopRes.set(res);
    }

    public void setLoopType(int loopType) {
        typeLoop.set(loopType);
        switch (loopType) {
            case LoopType.LOOP_ONE:
                setLoopRes(R.drawable.ic_loop_one);
                break;
            case LoopType.LOOP_ALL:
                setLoopRes(R.drawable.ic_loop_all);
                break;
            case LoopType.NO_LOOP:
                setLoopRes(R.drawable.ic_loop);
                break;
        }
    }
}
