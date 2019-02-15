package com.framgia.music_51.screen.detail_genre;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemCollectionBinding;
import com.framgia.music_51.databinding.ItemLoadingBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    private List mTracks;
    private Context mContext;

    DetailAdapter(Context context) {
        mTracks = new ArrayList<>();
        mContext = context;
    }

    public void setData(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.addAll(tracks);
        notifyItemRangeChanged(mTracks.size(), tracks.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_ITEM) {
        ItemCollectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_collection, viewGroup, false);
            return new ViewHolder(binding);
        }
        ItemLoadingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_loading, viewGroup, false);
        return new LoadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == VIEW_TYPE_ITEM) {
            ((ViewHolder) viewHolder).bindTrack(mContext, (Track) mTracks.get(i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mTracks.get(position) instanceof Track ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    void addLoadingIndicator() {
        mTracks.add(VIEW_TYPE_LOADING);
        notifyItemInserted(mTracks.size() - 1);
    }

    void removeLoadingIndicator() {
        int index = mTracks.indexOf(VIEW_TYPE_LOADING);
        if (index == -1) {
            return;
        }
        mTracks.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCollectionBinding mBinding;

        ViewHolder(ItemCollectionBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTrack(Context context, Track track) {
            mBinding.setTrack(track);
            mBinding.setHanlderClick(new HanlderClickItem(context, mTracks));
            mBinding.executePendingBindings();
        }
    }

    static class LoadViewHolder extends RecyclerView.ViewHolder {

        LoadViewHolder(ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }
}
