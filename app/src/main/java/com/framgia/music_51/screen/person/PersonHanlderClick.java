package com.framgia.music_51.screen.person;

import android.content.Context;
import android.content.Intent;

import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.service.TrackService;

import java.util.List;

public class PersonHanlderClick {
    private Context mContext;
    private List<Track> mTracks;

    public PersonHanlderClick(Context context, List<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    public void onClickDownload(Track track) {
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
        Intent intentService = TrackService.getMusicServiceIntent(mContext,
                mTracks.indexOf(track), mTracks, Utils.TYPE_LOCAL);
        mContext.startService(intentService);
    }
}
