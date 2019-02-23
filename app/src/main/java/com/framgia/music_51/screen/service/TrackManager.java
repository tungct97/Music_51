package com.framgia.music_51.screen.service;

import android.app.DownloadManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.framgia.music_51.BuildConfig;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.LoopType;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.play.OnUpdateUIListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;

public class TrackManager implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private static String BASE_URL_PLAY_MUSIC = "http://api.soundcloud.com/tracks/";
    private static String URL = "/stream?client_id=";
    private static MediaPlayer mMediaPlayer;
    private List<Track> mTracks;
    private List<Track> mUnShuffleTracks;
    private Context mContext;
    private OnUpdateUIListener mListener;
    private String mUrl;
    private int mPosition;
    private PlayMode mPlayMode;
    private int mTypeTrack;

    public TrackManager(Context context, ArrayList<Track> tracks, int position, int type) {
        mContext = context;
        mTypeTrack = type;
        mTracks = tracks;
        mPosition = position;
        mPlayMode = PlayMode.getInstance();
        mMediaPlayer = new MediaPlayer();
        mUnShuffleTracks = new ArrayList<>();
        mUnShuffleTracks.addAll(mTracks);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (mPlayMode.getLoopMode()) {
            case LoopType.LOOP_ONE:
                setData(mTracks);
                break;
            case LoopType.LOOP_ALL:
            case LoopType.NO_LOOP:
                next();
                break;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if (mListener == null) {
            return;
        }
        mListener.updateStateButton(mMediaPlayer.isPlaying());
        mListener.onUpdateSeekbar();
    }

    private void buildUrlPlayMusic(int trackId, int type) {
        if (type == Utils.TYPE_REMOTE) {
            urlRemote(trackId);
        } else {
            mUrl = String.valueOf(Environment.getExternalStoragePublicDirectory(
                    Utils.DOWNLOAD + Utils.FOLDER + File.separator + Utils.TRACK
                            + File.separator + getTrackCurrent().getTitle() + Utils.MP3));
        }
    }

    private String urlRemote(int trackId) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_URL_PLAY_MUSIC)
                .append(trackId)
                .append(URL)
                .append(BuildConfig.API_KEY);
        return mUrl = builder.toString();
    }

    public void setData(List<Track> tracks) {
        try {
            if (mMediaPlayer.isPlaying() || mMediaPlayer != null) {
                destroyMediaPlayer();
            }
            mMediaPlayer = new MediaPlayer();
            buildUrlPlayMusic(tracks.get(mPosition).getId(), mTypeTrack);
            mMediaPlayer.setDataSource(mContext, Uri.parse(mUrl));
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroyMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void setUiListener(OnUpdateUIListener listener) {
        mListener = listener;
    }

    public void play() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
        mListener.updateStateButton(isPlaying());
    }

    public void setLoop(@LoopType int loopType) {
        switch (loopType) {
            case LoopType.NO_LOOP:
                noLoop();
                break;
            case LoopType.LOOP_ONE:
                loopOne();
                break;
            case LoopType.LOOP_ALL:
                loopAll();
                break;
        }
    }

    public void next() {
        if (mPosition == mTracks.size() - 1) {
            if (mPlayMode.getLoopMode() == LoopType.LOOP_ALL) {
                mPosition = 0;
            } else {
                return;
            }
        } else {
            mPosition++;
        }
        mListener.onUpdateUiPlay(getTrackCurrent());
        setData(mTracks);
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void previous() {
        if (mPosition > 0) {
            mPosition--;
        } else {
            mPosition = 0;
        }
        mListener.onUpdateUiPlay(getTrackCurrent());
        setData(mTracks);
    }

    public void loopOne() {
        mMediaPlayer.setLooping(true);
        mPlayMode.setLoopMode(LoopType.LOOP_ONE);
        mListener.onLoopStateChange(LoopType.LOOP_ONE);
    }

    public void loopAll() {
        mMediaPlayer.setLooping(false);
        mPlayMode.setLoopMode(LoopType.LOOP_ALL);
        mListener.onLoopStateChange(LoopType.LOOP_ALL);
    }

    public void noLoop() {
        mPlayMode.setLoopMode(LoopType.NO_LOOP);
        mListener.onLoopStateChange(LoopType.NO_LOOP);
    }

    public Track getTrackCurrent() {
        return mTracks.get(mPosition);
    }

    public void seekTo(int position) {
        mMediaPlayer.seekTo(position);
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void checkShuffleMode(boolean shuffle) {
        if (!shuffle) {
            shuffle();
        } else {
            unShuffle();
        }
    }

    public void like(boolean like) {
        if (like) {
            mListener.onLikeStateChange(false);
        } else {
            mListener.onLikeStateChange(true);
        }
    }
    private void shuffle() {
        Track track = mTracks.get(mPosition);
        mTracks.remove(mPosition);
        shuffleList(mTracks, new Random());
        mTracks.add(mPosition, track);
        mListener.onShuffleStateChange(true);
    }

    private void unShuffle() {
        mTracks.clear();
        mTracks.addAll(mUnShuffleTracks);
        mPosition = mTracks.indexOf(mTracks.get(mPosition));
        mListener.onShuffleStateChange(false);
    }

    private void shuffleList(List<Track> tracks, Random random) {
        @SuppressWarnings("unchecked") final List<Track> trackSwap = tracks;
        if (tracks instanceof RandomAccess) {
            for (int i = trackSwap.size() - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                trackSwap.set(index, trackSwap.set(i, trackSwap.get(index)));
            }
        } else {
            Track[] array = (Track[]) trackSwap.toArray();
            for (int i = array.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                Track temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }
            int i = 0;
            ListIterator<Track> it = trackSwap.listIterator();
            while (it.hasNext()) {
                it.next();
                it.set(array[i++]);
            }
        }
        mTracks = new ArrayList<>();
        mTracks.addAll(trackSwap);
    }

    public void download() {
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(urlRemote(getTrackCurrent().getId()));
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator + Utils.FOLDER + File.separator + Utils.TRACK;
        File saveFile = new File(path);
        File parentDest = saveFile.getParentFile();
        if (!parentDest.exists()) {
            parentDest.mkdirs();
        }
        String stringDir = Utils.FOLDER + File.separator + Utils.TRACK + File.separator +
                getTrackCurrent().getTitle() + Utils.MP3;
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(getTrackCurrent().getTitle());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, stringDir);
        downloadManager.enqueue(request);
    }

}
