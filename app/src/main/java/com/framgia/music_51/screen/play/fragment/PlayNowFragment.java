package com.framgia.music_51.screen.play.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.FragmentPlayNowBinding;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.play.MediaListener;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.play.adapter.PlayNowAdapter;
import com.framgia.music_51.screen.service.TrackService;

import java.util.List;

import static com.framgia.music_51.screen.play.PlayerActivity.BUNDLE_TRACK;
import static com.framgia.music_51.screen.play.PlayerActivity.EXTRA_TRACK;

public class PlayNowFragment extends Fragment
        implements MediaListener.OnChangePlayNow, PlayNowAdapter.OnItemPlaylistClickListener, PlayerActivity.UpdateUIListener {
    private FragmentPlayNowBinding mBinding;
    private MediaListener mISongService;
    private boolean mMusicBound;
    private PlayNowAdapter playNowAdapter;

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) service;
            TrackService songService = binder.getService();
            mISongService = songService.getListener();
            if (mISongService == null) {
                return;
            }
            songService.setOnChangePlayNow(PlayNowFragment.this);
            if (mISongService.getListTrack() == null) {
                return;
            }
            List<Track> tracks = mISongService.getListTrack();
            int position = mISongService.getCurrentPosition();
            initRecyclerView(tracks, position);
            mMusicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Intent mPlayIntent = new Intent(getActivity(), TrackService.class);
        if (getContext() == null) {
            return;
        }
        getContext().bindService(mPlayIntent, mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        if (!mMusicBound) {
            if (getContext() == null) {
                return;
            }
            getContext().unbindService(mMusicConnection);
            mMusicBound = false;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_play_now, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onTrackChange(Track track, int position) {

    }

    @Override
    public void onListChange(List<Track> tracks) {
        initRecyclerView(tracks, 0);
    }

    private void initRecyclerView(List<Track> tracks, int i) {
        playNowAdapter = new PlayNowAdapter(getContext(), tracks, this, i);
        playNowAdapter.setPosition(mISongService.possotion());
        mBinding.recyclerPlayNow.setAdapter(playNowAdapter);
    }

    @Override
    public void onItemPlayNowClick(List<Track> tracks, int position) {
        Intent intentService = TrackService.getMusicServiceIntent(
                getContext(),
                position, tracks, Utils.TYPE_REMOTE);
        if (getActivity() == null) {
            return;
        }
        getActivity().startService(intentService);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_TRACK, tracks.get(position));
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        intent.putExtra(BUNDLE_TRACK, bundle);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void updateUI(Track track, int possotion) {
        playNowAdapter.setPosition(possotion);
        mBinding.recyclerPlayNow.setAdapter(playNowAdapter);
        mBinding.recyclerPlayNow.scrollToPosition(possotion);
    }
}

