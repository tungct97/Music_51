package com.framgia.music_51.screen.play.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.PlayMode;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.FragmentControlPlayBinding;
import com.framgia.music_51.screen.play.HandlerClick;
import com.framgia.music_51.screen.play.MediaListener;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.play.PlayerViewModel;
import com.framgia.music_51.screen.service.TrackService;


public class PlayControlFragment extends Fragment implements PlayerActivity.UpdateUIListener {
    private FragmentControlPlayBinding mBinding;
    private static final String TRACK = "TRACK";
    private boolean mMusicBound;
    private PlayerViewModel mViewModel;
    private PlayMode mPlayMode;
    private MediaListener mListener;
    private Track mTrack;

    public static PlayControlFragment newInstance(Track track) {
        PlayControlFragment fragment = new PlayControlFragment();
        Bundle args = new Bundle();
        args.putParcelable(TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_control_play, container, false);
        mTrack = getArguments().getParcelable(TRACK);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        mBinding.setTrack(mTrack);
        mBinding.setViewModel(mViewModel);
        mPlayMode = mViewModel.getPlayMode();
        mViewModel.getCurrentTrack().observe(getActivity(), new Observer<Track>() {
            @Override
            public void onChanged(@Nullable Track track) {
                Log.d("TAG1", track.getTitle());
            }
        });
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.disc_rotate);
        mBinding.imageMusicPlayer.startAnimation(animation);

    }

    private ServiceConnection mMusicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackService.TrackBinder binder = (TrackService.TrackBinder) service;
            TrackService songService = binder.getService();
            Track track = songService.getCurrentTrack();
            mListener = songService.getListener();
            mBinding.setHandlerClick(new HandlerClick(getActivity(), mListener, mPlayMode, mViewModel, songService));
            if (track != null) {
                Glide.with(getActivity()).load(mTrack.getArtworkUrl()).into(mBinding.imageMusicPlayer);
            }
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
        if (getContext() == null) {
            return;
        }
        Intent mPlayIntent = new Intent(getActivity(), TrackService.class);
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

    @Override
    public void updateUI(Track track, int possotion) {
        mBinding.setTrack(track);
      }

//    private void updateUI(Track track) {
//        Uri uri;
//        if (getContext() == null) {
//            return;
//        }
//        if (track.getArtworkUrl() != null && !track.getArtworkUrl().equals("null")) {
//            uri = Uri.parse(track.getArtworkUrl());
//        } else {
//            uri = Uri.parse(Constants.URI_IMAGE);
//        }
//        Utils.roundImageWithFresco(getContext(), mImageSong, uri);
//        //Downloadable
//        if (track.isDownloadable()) {
//            mImageDownload.setVisibility(View.VISIBLE);
//        } else {
//            mImageDownload.setVisibility(View.GONE);
//        }
//        //UPDATE FAVORITES
//        int favorites;
//        if (RealmFavoritesSong.getTrack(track) != null) {
//            favorites = FAVORITES;
//        } else {
//            favorites = UN_FAVORITES;
//        }
//        updateFavorites(favorites);
//        mPlayMode.setFavoritesMode(favorites);
//        //UPDATE DOWNLOAD
//        int download;
//        if (RealmDownloadSong.getTrack(track) != null) {
//            download = DOWNLOAD_DISABLE;
//        } else {
//            download = DOWNLOAD_ABLE;
//        }
//        updateStateDownload(download);
//        mPlayMode.setDownload(download);
//    }


}
