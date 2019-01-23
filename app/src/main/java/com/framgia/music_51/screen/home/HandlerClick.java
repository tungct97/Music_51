package com.framgia.music_51.screen.home;

import android.content.Context;

import com.framgia.music_51.screen.detail_genre.DetailGenreActivity;
import com.framgia.music_51.screen.SearchActivity;

public class HandlerClick {
    private Context mContext;

    public HandlerClick(Context context) {
        mContext = context;
    }

    public void onClickNavigatorSearchScreen() {
        mContext.startActivity(SearchActivity.getIntent(mContext));
    }

    public void onClickNavigatorDetailScreen() {
        mContext.startActivity(DetailGenreActivity.getIntent(mContext));
    }
}
