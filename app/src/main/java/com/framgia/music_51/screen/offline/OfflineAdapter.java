package com.framgia.music_51.screen.offline;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemDownloadBinding;
import com.framgia.music_51.screen.person.PersonHanlderClick;

import java.util.ArrayList;
import java.util.List;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.OfflineViewHolder> {
    public List<Track> mTracks;
    private Context mContext;

    public OfflineAdapter(Context context) {
        mTracks = new ArrayList<>();
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
    public OfflineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemDownloadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_download, viewGroup, false);
        return new OfflineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineViewHolder offlineViewHolder, int i) {
        offlineViewHolder.setBinding(mContext, mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    class OfflineViewHolder extends RecyclerView.ViewHolder {
        private ItemDownloadBinding mBinding;

        public OfflineViewHolder(ItemDownloadBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setBinding(Context context, Track track) {
            mBinding.setTrack(track);
            mBinding.setHanlderClick(new PersonHanlderClick(context, mTracks));
        }
    }
}
