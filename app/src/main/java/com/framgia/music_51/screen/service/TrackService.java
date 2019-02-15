package com.framgia.music_51.screen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;

import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.play.MediaListener;
import com.framgia.music_51.screen.play.OnUpdateUIListener;

import java.util.ArrayList;
import java.util.List;

public class TrackService extends Service implements MediaListener {
    private static final String ACTION_NEXT_TRACK = "ACTION_NEXT_TRACK";
    private static final String ACTION_PREVIOUS_TRACK = "ACTION_PREVIOUS_TRACK";
    private static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static final String EXTRA_LIST_TRACK = "EXTRA_LIST_TRACK";
    private static final String ACTION_CHANGE_STATE = "ACTION_CHANGE_STATE";
    private final IBinder mIBinder = new TrackBinder();
    private TrackManager mTrackManager;
    private int mPosition;
    private ArrayList<Track> mTracks;

    public static Intent getMusicServiceIntent(Context context, int position, List<Track> tracks) {
        Intent intent = new Intent(context, TrackService.class);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putParcelableArrayListExtra(EXTRA_LIST_TRACK, (ArrayList<? extends Parcelable>) tracks);
        intent.setAction(START_FOREGROUND_SERVICE);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case START_FOREGROUND_SERVICE:
                    setTrack(intent);
                    mTrackManager = new TrackManager(getApplicationContext(), mTracks, mPosition);
                    setData(mTracks);
                    break;
                case ACTION_NEXT_TRACK:
                    next();
                    break;
                case ACTION_PREVIOUS_TRACK:
                    previous();
                    break;
                case ACTION_CHANGE_STATE:
                    play();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    private void setData(List<Track> tracks) {
        mTrackManager.setData(tracks);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mIBinder;
    }

    @Override
    public boolean isPlaying() {
        return mTrackManager.isPlaying();
    }

    @Override
    public void play() {
        mTrackManager.play();
    }

    @Override
    public void next() {
        mTrackManager.next();
    }

    @Override
    public void previous() {
        mTrackManager.previous();
    }

    @Override
    public void seek(int position) {
        mTrackManager.seekTo(position);
    }

    @Override
    public void loop(int type) {
        mTrackManager.setLoop(type);
    }

    @Override
    public void shuffle(boolean shuffle) {
        mTrackManager.checkShuffleMode(shuffle);
    }

    @Override
    public int getCurrentPosition() {
        return mTrackManager.getCurrentPosition();
    }

    @Override
    public Track getCurrentTrack() {
        return mTracks.get(mPosition);
    }

    @Override
    public int getDuration() {
        return mTrackManager.getDuration();
    }

    @Override
    public void download() {

    }

    @Override
    public void favouriteTrack() {

    }

    private void setTrack(Intent intent) {
        if (intent == null) {
            return;
        }
        mPosition = intent.getIntExtra(EXTRA_POSITION, 0);
        mTracks = intent.getParcelableArrayListExtra(EXTRA_LIST_TRACK);
        mTracks.get(mPosition);
    }

    public MediaListener getListener() {
        return this;
    }

    public void setUiListener(OnUpdateUIListener listener) {
        mTrackManager.setUiListener(listener);
    }

    public class TrackBinder extends Binder {
        public TrackService getService() {
            return TrackService.this;
        }
    }
}
