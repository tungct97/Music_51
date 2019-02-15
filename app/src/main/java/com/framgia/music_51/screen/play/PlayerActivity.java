package com.framgia.music_51.screen.play;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
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
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivityPlayerBinding;
import com.framgia.music_51.screen.service.TrackService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayerActivity extends AppCompatActivity implements OnUpdateUIListener, SeekBar.OnSeekBarChangeListener {
    private static final String EXTRA_TRACK = "EXTRA_TRACK";
    private static final String BUNDLE_TRACK = "BUNDLE_TRACK";
    private ActivityPlayerBinding mBinding;
    private Track mTrack;
    private boolean mÍsBinded;
    private TrackService mService;
    private PlayerViewModel mViewModel;

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
                mService.setUiListener(PlayerActivity.this);
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
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mViewModel.setData(mTrack);
        mBinding.setViewModel(mViewModel);
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
        super.onPause();
        if (mÍsBinded) {
            unbindService(mConnection);
            mÍsBinded = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void updateStateButton(boolean isPlaying) {

    }

    @Override
    public void onUpdateUiPlay(Track track) {

    }

    @Override
    public void onUpdateSeekbar() {

    }

    @Override
    public void onShuffleStateChange(boolean shuffle) {

    }

    @Override
    public void onLoopStateChange(int type) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
