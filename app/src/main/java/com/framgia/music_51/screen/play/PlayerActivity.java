package com.framgia.music_51.screen.play;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.framgia.music_51.BlurBuilder;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivityPlayerBinding;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.play.fragment.PlayControlFragment;
import com.framgia.music_51.screen.play.fragment.PlayNowFragment;
import com.framgia.music_51.screen.service.TrackService;
import com.framgia.music_51.screen.timer.TimerFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayerActivity extends AppCompatActivity implements OnUpdateUIListener, SeekBar.OnSeekBarChangeListener {
    public static final String EXTRA_TRACK = "EXTRA_TRACK";
    public static final String BUNDLE_TRACK = "BUNDLE_TRACK";
    private static final int NUM_PAGES = 2;
    private static final int CONVERT_MINISECOND = 1000;
    private PlayMode mPlayMode;
    private ActivityPlayerBinding mBinding;
    private Track mTrack;
    private int mPosition;
    private boolean mÍsBinded;
    private TrackService mService;
    private Handler mHandler = new Handler();
    private MediaListener mListener;
    private PlayerViewModel mViewModel;
    private Runnable mRunnable;
    private List<Fragment> mFragments;

    public static Intent getIntent(Context context, Track track) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_TRACK, track);
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(BUNDLE_TRACK, bundle);
        return intent;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) service;
            mService = binder.getService();
            mÍsBinded = true;
            if (mService != null) {
                mListener = mService.getListener();
                mService.setUiListener(PlayerActivity.this);
                mBinding.setHandlerClick(new HandlerClick(PlayerActivity.this, mListener, mPlayMode, mViewModel, mService));
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mÍsBinded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        mFragments = new ArrayList<>();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        initToolbar();
        initAnim();
        getTrack(getTrackIntent());
        initViewModel();
        displayBackgroundImage(mTrack);
    }

    private void initAnim() {
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), mFragments);
        mBinding.viewpager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = PlayControlFragment.newInstance(mTrack);
                    break;
                case 1:
                    fragment = new PlayNowFragment();
                    break;
            }
            mFragments.add(fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mBinding.setTrack(mTrack);
        mBinding.setViewModel(mViewModel);
        mPlayMode = mViewModel.getPlayMode();
        mBinding.seekBar.setOnSeekBarChangeListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbarPlayer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbarPlayer.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Bundle getTrackIntent() {
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_TRACK);
        return bundle;
    }

    private Track getTrack(Bundle bundle) {
        mTrack = bundle.getParcelable(EXTRA_TRACK);
        return mTrack;
    }

    private void displayBackgroundImage(Track track) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            BlurBuilder.getBimap(track.getArtworkUrl(), this).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap bitmap) throws Exception {
                    Bitmap blurBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
                    Glide.with(getApplicationContext()).load(blurBitmap).into(mBinding.imagePlayer);
                    Glide.with(getApplicationContext()).load(blurBitmap).into(mBinding.imagePlayer);
                }
            });
        } else {
            Glide.with(getApplicationContext()).load(track.getArtworkUrl()).into(mBinding.imagePlayer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_person:
                TimerFragment fragment = (TimerFragment) getSupportFragmentManager()
                        .findFragmentByTag(TimerFragment.TAG);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().show(fragment).commit();
                } else {
                    fragment = TimerFragment.newInstance();
                    fragment.show(getSupportFragmentManager(), fragment.getTag());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void connectService() {
        Intent intent = new Intent(PlayerActivity.this, TrackService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectService();
    }

    @Override
    protected void onPause() {
        if (mÍsBinded) {
            unbindService(mConnection);
            mÍsBinded = false;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroy();
        super.onDestroy();
    }

    @Override
    public void updateStateButton(boolean isPlaying) {
        mViewModel.setPlayState(isPlaying);
    }

    @Override
    public void onUpdateUiPlay(Track track) {
        for (Fragment fragment : mFragments) {
            ((UpdateUIListener) fragment).updateUI(track, mService.possotion());
        }
        mViewModel.setData(track);
        mViewModel.getCurrentTrack().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(@Nullable Track track) {
                mBinding.setTrack(track);
                displayBackgroundImage(track);
            }
        });
        onUpdateSeekbar();
        updateStateButton(mService.isPlaying());
    }

    @Override
    public void onUpdateSeekbar() {
        mViewModel.setMaxSeekBar(mService.getDuration() / CONVERT_MINISECOND);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mService != null) {
                    mPosition = mService.getCurrentPosition() / CONVERT_MINISECOND;
                    mViewModel.onSeekBarPositionChange(mPosition);
                }
                mHandler.postDelayed(mRunnable, CONVERT_MINISECOND);
            }
        };
        mHandler.postDelayed(mRunnable, CONVERT_MINISECOND);
    }

    @Override
    public void onLikeStateChange(boolean like) {
        mViewModel.setLike(!like);
    }

    @Override
    public void onShuffleStateChange(boolean shuffle) {
        mPlayMode.setShuffer(mPlayMode.isShuffer() ? false : true);
        mViewModel.setShuffle(mPlayMode.isShuffer());
        mViewModel.savePlayerMode(mPlayMode);
    }

    @Override
    public void onLoopStateChange(int type) {
        mViewModel.setLoopType(type);
        mPlayMode.setLoopMode(type);
        mViewModel.savePlayerMode(mPlayMode);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPosition = seekBar.getProgress();
        mViewModel.onSeekBarPositionChange(mPosition);
        mListener.seek(mPosition * CONVERT_MINISECOND);
    }

    public interface UpdateUIListener {
        void updateUI(Track track, int possotion);
    }
}
