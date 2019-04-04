package com.framgia.music_51.screen.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.NotificationTarget;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.screen.DateTimeUtils;
import com.framgia.music_51.screen.searchplay.MediaSearchListener;
import com.framgia.music_51.screen.searchplay.OnUpdateUISearchListener;
import com.framgia.music_51.screen.searchplay.PlaySearch;
import com.framgia.music_51.screen.timer.ControlMusic;

import java.util.ArrayList;
import java.util.List;

public class SearchService extends Service implements MediaSearchListener, ControlMusic {
    private static final String ACTION_NEXT_TRACK = "ACTION_NEXT_TRACK";
    private static final String ACTION_PREVIOUS_TRACK = "ACTION_PREVIOUS_TRACK";
    private static final String START_FOREGROUND_SERVICE = "START_FOREGROUND_SERVICE";
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static final String EXTRA_LIST_SEARCH = "EXTRA_LIST_SEARCH";
    private static final String ACTION_CHANGE_STATE = "ACTION_CHANGE_STATE";
    private static final String NOTIFICATION_CHANEL = "CHANNEL_ID_NOTIFY";
    public static final String ACTION_CLEAR = "ACTION_CLEAR";
    private static final int REQUEST_CODE_NEXT = 1;
    private static final int REQUEST_CODE_PREVIOUS = 2;
    private static final int REQUEST_CODE_PAUSE = 3;
    private static final int REQUEST_CODE_CLEAR = 4;
    private static final int ID_NOTIFICATION = 111;
    private final IBinder mIBinder = new SearchService.SearchBinder();
    private SearchManager mTrackManager;
    private int mPosition;
    private ArrayList<Search> mTracks;
    private Notification mNotification;
    private int mTypeTrack;
    private RemoteViews mRemoteViews;
    private CountDownTimer mCountDownTimer;

    public static Intent getMusicServiceIntent(Context context, int position, List<Search> tracks) {
        Intent intent = new Intent(context, SearchService.class);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putParcelableArrayListExtra(EXTRA_LIST_SEARCH, (ArrayList<? extends Parcelable>) tracks);
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
                    mTrackManager = new SearchManager(getApplicationContext(), mTracks, mPosition);
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
                case ACTION_CLEAR:
                    mTrackManager.destroyMediaPlayer();
                    stopForeground(true);
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    private void setData(List<Search> tracks) {
        mTrackManager.setData(tracks);
        createNotification(getCurrentTrack().getTitle(),
                "",
                getCurrentTrack().getArtworkUrl());
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
        updateNotificationState();
    }

    @Override
    public void next() {
        mTrackManager.next();
        updateNotificationChangeTrack(mTrackManager.getTrackCurrent());
    }

    @Override
    public void previous() {
        mTrackManager.previous();
        updateNotificationChangeTrack(mTrackManager.getTrackCurrent());
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
    public Search getCurrentTrack() {
        return mTracks.get(mPosition);
    }

    @Override
    public int getDuration() {
        return mTrackManager.getDuration();
    }

    @Override
    public void download() {
        mTrackManager.download();
    }

    @Override
    public void favouriteTrack(boolean like) {
        mTrackManager.like(like);
    }

    public void createNotification(String title, String artist, String avatar) {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        setDataForNotification(title, artist);
        buildNotify(avatar);
        createIntentNotify();
    }

    private void createIntentNotify() {
        createIntent(R.id.image_notify_next, ACTION_NEXT_TRACK, REQUEST_CODE_NEXT);
        createIntent(R.id.image_notify_previous, ACTION_PREVIOUS_TRACK,
                REQUEST_CODE_PREVIOUS);
        createIntent(R.id.image_notify_pause, ACTION_CHANGE_STATE, REQUEST_CODE_PAUSE);
        createIntent(R.id.image_notify_clear, ACTION_CLEAR, REQUEST_CODE_CLEAR);
    }

    private void createIntent(int id, String action, int requestCode) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setClass(getApplicationContext(), TrackService.class);
        PendingIntent pendingIntent =
                PendingIntent.getService(getApplicationContext(), requestCode, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(id, pendingIntent);
    }

    private void buildNotify(String avatar) {
        Intent intent = PlaySearch.getSearchIntent(getApplicationContext(), getCurrentTrack());
        PendingIntent pendingIntent =
                PendingIntent.getActivities(getApplicationContext(), (int) System.currentTimeMillis(),
                        new Intent[]{intent}, 0);
        Notification.Builder notificationBuilder =
                new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = notificationBuilder.setSmallIcon(R.drawable.ic_music_selected)
                    .setContentIntent(pendingIntent)
                    .setContent(mRemoteViews)
                    .setDefaults(Notification.FLAG_NO_CLEAR)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buildChannel(notificationBuilder);
        }
        loadImageNotification(avatar);
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private void loadImageNotification(String url) {
        NotificationTarget notificationTarget = new NotificationTarget(
                getApplicationContext(),
                R.id.image_notify_avatar,
                mRemoteViews,
                mNotification,
                ID_NOTIFICATION);
        NotificationTarget notificationTargetBackGround = new NotificationTarget(
                getApplicationContext(),
                R.id.image_background_notification,
                mRemoteViews,
                mNotification,
                ID_NOTIFICATION);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().error(R.drawable.image_default))
                .into(notificationTarget);
        Glide.with(getApplicationContext())
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().error(R.drawable.image_default))
                .into(notificationTargetBackGround);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildChannel(android.app.Notification.Builder notificationBuilder) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        CharSequence name = getString(R.string.app_name);
        NotificationChannel mChannel =
                new NotificationChannel(NOTIFICATION_CHANEL, name, importance);
        mNotification = notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setChannelId(NOTIFICATION_CHANEL)
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(ID_NOTIFICATION, mNotification);
        }
    }

    private void setDataForNotification(String title, String artist) {
        mRemoteViews.setTextViewText(R.id.text_notify_title, title);
        mRemoteViews.setTextViewText(R.id.text_notify_artist, artist);
        mRemoteViews.setImageViewResource(R.id.image_notify_next, R.drawable.ic_next);
        mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_pause_mini);
        mRemoteViews.setImageViewResource(R.id.image_notify_previous, R.drawable.ic_previous);
        mRemoteViews.setImageViewResource(R.id.image_notify_clear, R.drawable.ic_delete);
    }

    public void updateNotificationChangeTrack(Search track) {
        mRemoteViews.setTextViewText(R.id.text_notify_title, track.getTitle());
//        mRemoteViews.setTextViewText(R.id.text_notify_artist,
//                track.getPublisherMetadata().getArtist());
        loadImageNotification(track.getArtworkUrl());
        startForeground(ID_NOTIFICATION, mNotification);
    }

    public void updateNotificationState() {
        if (mTrackManager.isPlaying()) {
            mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_pause_mini);
        } else {
            mRemoteViews.setImageViewResource(R.id.image_notify_pause, R.drawable.ic_play_mini);
        }
        startForeground(ID_NOTIFICATION, mNotification);
    }

    private void setTrack(Intent intent) {
        if (intent == null) {
            return;
        }
        mPosition = intent.getIntExtra(EXTRA_POSITION, 0);
        mTracks = intent.getParcelableArrayListExtra(EXTRA_LIST_SEARCH);
        mTracks.get(mPosition);
    }

    public MediaSearchListener getListener() {
        return this;
    }

    public void setUiListener(OnUpdateUISearchListener listener) {
        mTrackManager.setUiListener(listener);
    }

    @Override
    public void countDown(TextView textTime, int time) {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mTrackManager.play();
                }
            };
        } else {
            mCountDownTimer.cancel();
            mCountDownTimer = new CountDownTimer(time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mTrackManager.play();
                }
            };
        }
        mCountDownTimer.start();
    }

    public class SearchBinder extends Binder {
        public SearchService getService() {
            return SearchService.this;
        }
    }
}
