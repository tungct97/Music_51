package com.framgia.music_51.screen.search;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.repository.SearchRepository;
import com.framgia.music_51.data.resource.SearchLocalDataSource;
import com.framgia.music_51.data.resource.SearchRemoteDataSource;
import com.framgia.music_51.data.resource.sql.TrackDataBase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends AndroidViewModel {
    private SearchRepository mRepository;
    private MutableLiveData<Collection> mDataTrack = new MutableLiveData<>();
    private TrackDataBase mTrackDataBase;
    private CompositeDisposable mDisposable;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        mDisposable = new CompositeDisposable();
        mTrackDataBase = TrackDataBase.getInstance(getApplication());
        mRepository = SearchRepository.getInstance(SearchRemoteDataSource.getInstance(),
                SearchLocalDataSource.getInstance(mTrackDataBase.trackDao()));
    }

    LiveData<Collection> getTrack(String q) {
        mRepository.getSearchTrack(q)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Collection>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Collection track) {
                        mDataTrack.setValue(track);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return mDataTrack;
    }

    public void saveData(final Track search) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) {
                mRepository.saveSearch(search);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
        mDisposable.add(disposable);
    }

    LiveData<List<Track>> getHistory() {
        return mRepository.getHistory();
    }

    public void deleteSearch(Track search) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) {
                mRepository.removeHistory(search);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
        mDisposable.add(disposable);
    }

    public void deleteAllSearch() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) {
                mRepository.deleteAllSearch();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
        mDisposable.add(disposable);
    }
}
