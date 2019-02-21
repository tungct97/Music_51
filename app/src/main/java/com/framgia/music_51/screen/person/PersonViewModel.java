package com.framgia.music_51.screen.person;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.repository.TrackRepository;
import com.framgia.music_51.data.resource.TrackLocalDataSource;
import com.framgia.music_51.data.resource.TrackRemoteDataSource;
import com.framgia.music_51.data.resource.sql.TrackDataBase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonViewModel extends AndroidViewModel {
    private TrackDataBase mTrackDataBase;
    private CompositeDisposable mCompositeDisposable;
    private TrackRepository mRepository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        mTrackDataBase = TrackDataBase.getInstance(getApplication());
        mRepository = TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
                TrackLocalDataSource.getInstance(mTrackDataBase.trackDao()));
    }

    LiveData<List<Track>> getFavourites() {
        return mRepository.getFavorites();
    }

    public void removeItemFavourite(final Track track) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                mRepository.removeFavorite(track);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
        mCompositeDisposable.add(disposable);
    }

    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.isDisposed();
        }
    }
}
