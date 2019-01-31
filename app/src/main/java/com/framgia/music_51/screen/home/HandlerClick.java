package com.framgia.music_51.screen.home;

import android.content.Context;

import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.detail_genre.DetailGenreActivity;
import com.framgia.music_51.screen.SearchActivity;
import com.framgia.music_51.screen.play.PlayerActivity;

public class HandlerClick {
    private Context mContext;

    public HandlerClick(Context context) {
        mContext = context;
    }

    public void onClickNavigatorSearchScreen() {
        mContext.startActivity(SearchActivity.getIntent(mContext));
    }

    public void onClickNavigatorDetailScreen(MusicResponse genres) {
        mContext.startActivity(DetailGenreActivity.getIntent(mContext, genres));
    }

    public void onClickNavigatorPlayerScreen(Track track){
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
    }
}
