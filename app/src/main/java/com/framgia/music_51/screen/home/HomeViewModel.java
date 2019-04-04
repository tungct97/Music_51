package com.framgia.music_51.screen.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.GenreType;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.repository.GenreRepository;
import com.framgia.music_51.data.resource.GenreRemoteDataSource;
import com.framgia.music_51.screen.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function9;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<MusicResponse>> mData = new MutableLiveData<>();
    private GenreRepository mGenreRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mGenreRepository = GenreRepository.getInstance(GenreRemoteDataSource.getInstance());
    }

    LiveData<List<MusicResponse>> getGenres() {
        Single.zip(mGenreRepository.getGenre(Utils.KIND, GenreType.ALL_MUSIC,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.ELECTRONIC,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.ALTERNATIVEROCK,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.AMBIENT,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.CLASSICAL,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.COUNTRY,1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.DANCEEDM, 1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.DRUMBASS, 1),
                mGenreRepository.getGenre(Utils.KIND, GenreType.DISCO, 1),
                new Function9<MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse, MusicResponse,
                        MusicResponse, List<MusicResponse>>() {
                    @Override
                    public List<MusicResponse> apply(MusicResponse genreAllMusic,
                                                     MusicResponse genreElectrolic,
                                                     MusicResponse genreAlternativerock,
                                                     MusicResponse genreAmbient,
                                                     MusicResponse genreClassical,
                                                     MusicResponse genreCountry,
                                                     MusicResponse genreDanceedm,
                                                     MusicResponse genreDrumbass,
                                                     MusicResponse genreDisco) throws Exception {
                        List<MusicResponse> genres = new ArrayList<>();
                        genres.add(genreAllMusic);
                        genres.add(genreElectrolic);
                        genres.add(genreAlternativerock);
                        genres.add(genreAmbient);
                        genres.add(genreClassical);
                        genres.add(genreCountry);
                        genres.add(genreDanceedm);
                        genres.add(genreDrumbass);
                        genres.add(genreDisco);
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
