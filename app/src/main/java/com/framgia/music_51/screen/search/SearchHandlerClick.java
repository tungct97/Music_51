package com.framgia.music_51.screen.search;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.screen.Utils;
import com.framgia.music_51.screen.play.PlayerActivity;
import com.framgia.music_51.screen.service.TrackService;

import java.util.List;

public class SearchHandlerClick {
    private Context mContext;
    private List<Track> mTracks;
    private SearchViewModel mViewModel;

    public SearchHandlerClick(Context context, List<Track> tracks, SearchViewModel searchViewModel) {
        mContext = context;
        mTracks = tracks;
        mViewModel = searchViewModel;
    }

    public SearchHandlerClick(Context context, List<Track> tracks) {
        mContext = context;
        mTracks = tracks;
    }

    public SearchHandlerClick(Context context) {
        mContext = context;
    }

    public void onClickNavigatorSearchScreen(Track track) {
        mViewModel.saveData(track);
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
        Intent intentService = TrackService.getMusicServiceIntent(mContext,
                mTracks.indexOf(track), mTracks, Utils.TYPE_REMOTE);
        mContext.startService(intentService);
    }

    public void onClickNavigatorScreen(Track track) {
        mContext.startActivity(PlayerActivity.getIntent(mContext, track));
        Intent intentService = TrackService.getMusicServiceIntent(mContext,
                mTracks.indexOf(track), mTracks, Utils.TYPE_REMOTE);
        mContext.startService(intentService);
    }

    public void deleteAllSearch() {
        new AlertDialog.Builder(mContext)
                .setMessage("Ban có muốn xóa tất cả lịch sử không????")
                .setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.deleteAllSearch();
                    }
                })
                .setNegativeButton(R.string.title_no, null)
                .show();
    }

    public void deleteHistory(Track search) {
        mViewModel.deleteSearch(search);
        Toast.makeText(mContext, "Bạn đã xóa thành công", Toast.LENGTH_SHORT).show();
    }
}
