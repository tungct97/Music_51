package com.framgia.music_51.screen.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivityMainBinding;
import com.framgia.music_51.screen.PermistoinUtils;
import com.framgia.music_51.screen.offline.ListMusicFragment;
import com.framgia.music_51.screen.person.PersonFragment;
import com.framgia.music_51.screen.play.MediaListener;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.service.TrackService;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, MediaListener.OnMiniPlayerChangeListener, View.OnClickListener {
    private ActivityMainBinding mBinding;
    private PermistoinUtils mPermistoinUtils;
    private TrackService mTrackService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) service;
            mTrackService = binder.getService();
            mTrackService.setMiniPlayer(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addFragment(HomeFragment.TAG);
        mPermistoinUtils = new PermistoinUtils(this);
        mPermistoinUtils.requestPermissionImage();

    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, TrackService.class));
        bindService(new Intent(this, TrackService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String selectedTag;
        switch (item.getItemId()) {
            default:
            case R.id.nav_home:
                selectedTag = HomeFragment.TAG;
                break;
            case R.id.nav_list:
                selectedTag = ListMusicFragment.TAG;
                break;
            case R.id.nav_person:
                selectedTag = PersonFragment.TAG;
                break;
        }
        addFragment(selectedTag);
        return true;
    }

    private void addFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
            return;
        }
        addFragment(getFragment(tag), tag);
    }

    private Fragment getFragment(String tag) {
        Fragment fragment = null;
        switch (tag) {
            case HomeFragment.TAG:
                fragment = HomeFragment.newInstance();
                break;
            case ListMusicFragment.TAG:
                fragment = ListMusicFragment.newInstance();
                break;
            case PersonFragment.TAG:
                fragment = PersonFragment.newInstance();
                break;
        }
        return fragment;
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, tag).commit();
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        if (isPlaying) {
            mBinding.imagePlay.setImageResource(R.drawable.ic_pause_mini);
        } else {
            mBinding.imagePlay.setImageResource(R.drawable.ic_play_mini);
        }
    }

    @Override
    public void onTrackChange(Track track) {
        mBinding.setTrack(track);
        mBinding.constraintMiniPlay.setVisibility(View.VISIBLE);
        mBinding.titleMiniPlay.setText(track.getTitle());
        mBinding.authMiniPlay.setText(track.getPublisherMetadata().getArtist());
        mBinding.imagePlay.setOnClickListener(this);
        mBinding.imageNext.setOnClickListener(this);
        mBinding.constraintMiniPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_play:
                mTrackService.play();
                break;
            case R.id.image_next:
                mTrackService.next();
                break;
            case R.id.constraint_mini_play:
                startActivity(PlayerActivity.getIntent(this, mTrackService.getCurrentTrack()));
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }
}
