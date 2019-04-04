package com.framgia.music_51.screen.person;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemDownloadBinding;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder> {
    public List<Track> mTracks;
    private Context mContext;
    private DownloadHanlderClick mHanlderClick;

    public DownloadAdapter(Context context, DownloadHanlderClick downloadHanlderClick) {
        mTracks = new ArrayList<>();
        mHanlderClick = downloadHanlderClick;
        mContext = context;
    }

    public void setData(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DownloadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemDownloadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_download, viewGroup, false);
        return new DownloadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadViewHolder downloadViewHolder, int i) {
        downloadViewHolder.setBinding(mContext, mTracks.get(i), mHanlderClick);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    class DownloadViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private ItemDownloadBinding mBinding;
        private DownloadHanlderClick mHanlderClick;
        private Track mTrack;

        public DownloadViewHolder(ItemDownloadBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setBinding(Context context, Track track, DownloadHanlderClick downloadHanlderClick) {
            mTrack = track;
            mHanlderClick = downloadHanlderClick;
            mBinding.layoutDownload.setOnLongClickListener(this);
            mBinding.setTrack(track);
            mBinding.setHanlderClick(new PersonHanlderClick(context, mTracks));
        }

        @Override
        public boolean onLongClick(View v) {
            Log.d("TAG1", "Vào");
            mHanlderClick.removeDownload(mTrack);
            return false;
        }
    }

    public interface DownloadHanlderClick {
        void removeDownload(Track track);
    }
}
