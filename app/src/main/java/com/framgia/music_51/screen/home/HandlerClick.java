package com.framgia.music_51.screen.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.detail_genre.DetailGenreActivity;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.search.SearchActivity;
import com.framgia.music_51.screen.service.TrackService;

import java.util.List;

public class HandlerClick {
    private Context mContext;
    private List<Track> mTracks;

    public HandlerClick(Context context) {
        mContext = context;
    }

    public HandlerClick(Context context, List<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    public void onClickNavigatorSearchScreen() {
        mContext.startActivity(SearchActivity.getIntent(mContext));
    }

    public void onClickNavigatorDetailScreen(MusicResponse genres) {
        mContext.startActivity(DetailGenreActivity.getIntent(mContext, genres));
    }

    public void onClickNavigatorPlayerScreen(Track track){
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
        Intent intentService = TrackService.getMusicServiceIntent(mContext,
                mTracks.indexOf(track), mTracks, Utils.TYPE_REMOTE);
        mContext.startService(intentService);
    }
}
