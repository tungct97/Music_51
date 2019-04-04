package com.framgia.music_51.screen.searchplay;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.data.repository.PlayModeRepository;
import com.framgia.music_51.data.repository.TrackRepository;
import com.framgia.music_51.data.resource.PlayModeLocalDataSource;
import com.framgia.music_51.data.resource.sql.TrackDataBase;

import io.reactivex.disposables.CompositeDisposable;

public class PlaySearchViewModel extends AndroidViewModel {
    public ObservableField<Integer> positionSeekBar = new ObservableField<>();
    public ObservableField<Integer> maxSeekBar = new ObservableField<>();
    public ObservableField<Boolean> isShuffle = new ObservableField<>(false);
    public ObservableField<Integer> typeLoop = new ObservableField<>();
    public ObservableField<Boolean> playState = new ObservableField<>();
    public ObservableField<Integer> loopRes = new ObservableField<>();
    public ObservableField<Boolean> isLike = new ObservableField<>(false);
    private MutableLiveData<Search> mCurrentTrack;
    private PlayModeRepository mRepository;
//    private TrackRepository mTrackRepository;
//    private TrackDataBase mTrackDataBase;
    private CompositeDisposable mCompositeDisposable;


    public PlaySearchViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
        mRepository = PlayModeRepository.getInstance(PlayModeLocalDataSource.getInstance(application));
//        mTrackDataBase = TrackDataBase.getInstance(getApplication());
//        mTrackRepository = TrackRepository.getInstance(TrackRemoteDataSource.getInstance(),
//                TrackLocalDataSource.getInstance(mTrackDataBase.trackDao()));
    }

    public MutableLiveData<Search> getCurrentTrack() {
        if (mCurrentTrack == null) {
            mCurrentTrack = new MutableLiveData<>();
        }
        return mCurrentTrack;
    }

    public void setData(Search track) {
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

    public void setLike(boolean like) {
        isLike.set(like);
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

    public void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.isDisposed();
        }
    }

//    public void isFavourite(final Track track, final boolean favourite) {
//        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                if (favourite) {
//                    track.setFavourite(true);
//                    mTrackRepository.addFavorite(track);
//                } else {
//                    track.setFavourite(false);
//                    mTrackRepository.removeFavorite(track);
//                }
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe();
//        mCompositeDisposable.add(disposable);
//    }
//
//    public void saveData(final Track track) {
//        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                mTrackRepository.addDownload(getTrack(track));
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe();
//        mCompositeDisposable.add(disposable);
//    }
//
//    private Track getTrack(Track track) {
//        String stringDir = String.valueOf(Environment.getExternalStoragePublicDirectory(
//                Utils.DOWNLOAD + Utils.FOLDER + File.separator + Utils.TRACK
//                        + File.separator + track.getTitle() + Utils.MP3));
//        track.setDownloadUrl(stringDir);
//        return track;
//    }
}
