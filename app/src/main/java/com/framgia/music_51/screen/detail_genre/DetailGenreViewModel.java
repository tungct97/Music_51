package com.framgia.music_51.screen.detail_genre;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.repository.TrackRepository;
import com.framgia.music_51.data.resource.TrackLocalDataSource;
import com.framgia.music_51.data.resource.TrackRemoteDataSource;
import com.framgia.music_51.data.resource.sql.TrackDataBase;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailGenreViewModel extends AndroidViewModel {
    private MutableLiveData<MusicResponse> mData = new MutableLiveData<>();
    private TrackRepository mTrackRepository;
    private TrackDataBase mTrackDataBase;

    public DetailGenreViewModel(@NonNull Application application) {
        super(application);
        mTrackDataBase = TrackDataBase.getInstance(getApplication());
        mTrackRepository = TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                TrackLocalDataSource.getInstance(mTrackDataBase.trackDao()));
    }

    LiveData<MusicResponse> getTracks(String kind, @GenreType String type, int offset) {
        mTrackRepository.getTracks(kind, type, offset).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new SingleObserver<MusicResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(MusicResponse musicResponse) {
                mData.setValue(musicResponse);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return mData;
    }
}
