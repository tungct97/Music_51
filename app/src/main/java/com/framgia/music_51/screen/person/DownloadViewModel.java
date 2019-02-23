package com.framgia.music_51.screen.person;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.repository.TrackRepository;
import com.framgia.music_51.data.resource.TrackLocalDataSource;
import com.framgia.music_51.data.resource.TrackRemoteDataSource;
import com.framgia.music_51.data.resource.sql.TrackDataBase;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class DownloadViewModel extends AndroidViewModel {
    private TrackDataBase mTrackDataBase;
    private CompositeDisposable mCompositeDisposable;
    private TrackRepository mRepository;

    public DownloadViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        mTrackDataBase = TrackDataBase.getInstance(getApplication());
        mRepository = TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                TrackLocalDataSource.getInstance(mTrackDataBase.trackDao()));
    }

    LiveData<List<Track>> getDownloads() {
        return mRepository.getDownload();
    }

    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.isDisposed();
        }
    }
}

