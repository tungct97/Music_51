package com.framgia.music_51.screen.play;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.model.Track;

public class PlayerViewModel extends AndroidViewModel {
    private MutableLiveData<Track> mCurrentTrack;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
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
}
