package com.framgia.music_51.screen.detail_genre;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EnlessScrollListener extends RecyclerView.OnScrollListener {
    private boolean mIsLoading;
    private int mScollCount;
    private LinearLayoutManager mLayoutManager;

    public EnlessScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                int totalItemCount = mLayoutManager.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int visibleThreshold = 5;
                if (totalItemCount <= (lastVisibleItem + visibleThreshold) ) {
                    this.onLoadMore();
                }
                mIsLoading = true;
                mScollCount++;
        }
    }

    public abstract void onLoadMore();
}
