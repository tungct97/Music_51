package com.framgia.music_51.screen.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.BuildConfig;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.repository.GenreRepository;
import com.framgia.music_51.data.resource.GenreRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function6;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<MusicResponse>> mData = new MutableLiveData<>();
    private GenreRepository mGenreRepository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        mGenreRepository = GenreRepository.getInstance(GenreRemoteDataSource.getInstance());
    }

    LiveData<List<MusicResponse>> getGenres() {
        Single.zip(mGenreRepository.getGenre(Utils.KIND, GenreType.ALL_MUSIC),
                mGenreRepository.getGenre(Utils.KIND, GenreType.ALL_AUDIO),
                mGenreRepository.getGenre(Utils.KIND, GenreType.ALTERNATIVEROCK),
                mGenreRepository.getGenre(Utils.KIND, GenreType.AMBIENT),
                mGenreRepository.getGenre(Utils.KIND, GenreType.CLASSICAL),
                mGenreRepository.getGenre(Utils.KIND, GenreType.COUNTRY),
                new Function6<MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse, List<MusicResponse>>() {
                    @Override
                    public List<MusicResponse> apply(MusicResponse genreAllMusic,
                                                     MusicResponse genreAllAudio,
                                                     MusicResponse genreAlternativerock,
                                                     MusicResponse genreAmbient,
                                                     MusicResponse genreClassical,
                                                     MusicResponse genreCountry) throws Exception {
                        List<MusicResponse> genres = new ArrayList<>();
                        genres.add(genreAllMusic);
                        genres.add(genreAllAudio);
                        genres.add(genreAlternativerock);
                        genres.add(genreAmbient);
                        genres.add(genreClassical);
                        genres.add(genreCountry);
                        return genres;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<List<MusicResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MusicResponse> musicRespons) {
                        mData.setValue(musicRespons);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return mData;
    }
}
