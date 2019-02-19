package com.framgia.music_51.screen.person;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemPresonBinding;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ListMusicViewHolder>{
    private List<Track> mTracks;
    private PersonHanlderClick mHanlderClick;

    public PersonAdapter(PersonHanlderClick hanlderClick) {
        mTracks = new ArrayList<>();
        mHanlderClick = hanlderClick;
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
    public ListMusicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPresonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_preson, viewGroup, false);
        return new ListMusicViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ListMusicViewHolder listMusicViewHolder, int i) {
        listMusicViewHolder.setBinding(mTracks.get(i), mHanlderClick);
    }

    static class ListMusicViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private ItemPresonBinding mBinding;
        private PersonHanlderClick mHanlderClick;
        private Track mTrack;

        public ListMusicViewHolder(ItemPresonBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.layoutPerson.setOnLongClickListener(this);
        }

        public void setBinding(Track track, PersonHanlderClick hanlderClick) {
            if (track.isFavourite() == true) {
                mTrack = track;
                mHanlderClick = hanlderClick;
                mBinding.setTrack(track);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            mHanlderClick.removeFavourite(mTrack);
            return false;
        }
    }

    public interface PersonHanlderClick {
        void removeFavourite(Track track);
    }
}
