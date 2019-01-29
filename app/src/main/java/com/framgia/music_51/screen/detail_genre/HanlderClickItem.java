package com.framgia.music_51.screen.detail_genre;

import android.content.Context;

import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.play.PlayerActivity;

public class HanlderClickItem {
    private Context mContext;

    public HanlderClickItem(Context context) {
        mContext = context;
    }

    public void onClickNavigatorPlayScreen(Track track) {
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
    }
}
