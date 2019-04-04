package com.framgia.music_51.screen.searchplay;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.framgia.music_51.BlurBuilder;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.databinding.ActivityPlaySearchBinding;
import com.framgia.music_51.screen.service.SearchService;
import com.framgia.music_51.screen.timer.TimerFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlaySearch extends AppCompatActivity implements OnUpdateUISearchListener, SeekBar.OnSeekBarChangeListener {
    private static final String EXTRA_SEARCH = "EXTRA_SEARCH";
    private static final String BUNDLE_SEARCH = "BUNDLE_SEARCH";
    private static final int CONVERT_MINISECOND = 1000;
    private PlayMode mPlayMode;
    private ActivityPlaySearchBinding mBinding;
    private Search mTrack;
    private int mPosition;
    private boolean mÍsBinded;
    private SearchService mService;
    private Handler mHandler = new Handler();
    private MediaSearchListener mListener;
    private PlaySearchViewModel mViewModel;
    private Runnable mRunnable;

    public static Intent getSearchIntent(Context context, Search track) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SEARCH, track);
        Intent intent = new Intent(context, PlaySearch.class);
        intent.putExtra(BUNDLE_SEARCH, bundle);
        return intent;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SearchService.SearchBinder binder = (SearchService.SearchBinder) service;
            mService = binder.getService();
            mÍsBinded = true;
            if (mService != null) {
                mListener = mService.getListener();
                mService.setUiListener(PlaySearch.this);
                mBinding.setHandlerClick(new HandlerClick(PlaySearch.this, mListener, mPlayMode, mViewModel, mService));
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_play_search);
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
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        mBinding.imageMusicPlayer.startAnimation(animation);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(PlaySearchViewModel.class);
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
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_SEARCH);
        return bundle;
    }

    private Search getTrack(Bundle bundle) {
        mTrack = bundle.getParcelable(EXTRA_SEARCH);
        return mTrack;
    }

    private void displayBackgroundImage(Search track) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            BlurBuilder.getBimap(track.getArtworkUrl(), this).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap bitmap) {
                    Bitmap blurBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
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
                    Log.d("TAG1", "VÀo");
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
        Intent intent = new Intent(PlaySearch.this, SearchService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mÍsBinded) {
            unbindService(mConnection);
            mÍsBinded = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }

    @Override
    public void updateStateButton(boolean isPlaying) {
        mViewModel.setPlayState(isPlaying);
    }

    @Override
    public void onUpdateUiPlay(Search track) {
        mViewModel.setData(track);
        mViewModel.getCurrentTrack().observe(this, new android.arch.lifecycle.Observer<Search>() {
            @Override
            public void onChanged(@Nullable Search search) {
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
        mViewModel.onSeekBarPositionChange(seekBar.getProgress());
        mListener.seek(mPosition * CONVERT_MINISECOND);
    }
}
