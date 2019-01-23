package com.framgia.music_51.screen.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemGenresBinding;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private List<MusicResponse> mGenres;
    private Context mContext;

    public GenreAdapter(Context context) {
        mContext = context;
        mGenres = new ArrayList<>();
    }

    public void setData(List<MusicResponse> genres) {
        if (genres == null) {
            return;
        }
        mGenres.addAll(genres);
        int start = mGenres.size();
        notifyItemRangeChanged(start, genres.size());
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemGenresBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_genres, viewGroup, false);
        return new GenreViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder genreViewHolder, int i) {
        genreViewHolder.setBinding(mContext,     mGenres.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        private ItemGenresBinding mBinding;
        private TrackAdapter mAdapter;

        public GenreViewHolder(ItemGenresBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setBinding(Context context, MusicResponse genre) {
            mAdapter = new TrackAdapter();
            mAdapter.setData(getTracks(genre));
            mBinding.recyclerTrack.setAdapter(mAdapter);
            mBinding.setGenre(genre);
            mBinding.setHandlerClick(new HandlerClick(context));
            mBinding.executePendingBindings();
        }

        private List<Track> getTracks(MusicResponse musicResponse) {
            List<Track> tracks = new ArrayList<>();
            for (Collection collection : musicResponse.getCollections()) {
                tracks.add(collection.getTrack());
            }
            return tracks;
        }
    }
}
