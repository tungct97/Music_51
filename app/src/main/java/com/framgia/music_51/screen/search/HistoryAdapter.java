package com.framgia.music_51.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ItemHistorySearchBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Track> mSearches;
    private Context mContext;
    private SearchViewModel mViewModel;

    public HistoryAdapter(Context context, SearchViewModel searchViewModel) {
        mSearches = new ArrayList<>();
        mContext = context;
        mViewModel = searchViewModel;
    }

    public void setData(List<Track> searches) {
        if (searches == null) {
            return;
        }
        mSearches.clear();
        mSearches.addAll(searches);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemHistorySearchBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_history_search, viewGroup, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        historyViewHolder.setBinding(mContext, mSearches.get(i));
    }

    @Override
    public int getItemCount() {
        return mSearches != null ? mSearches.size() : 0;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ItemHistorySearchBinding mBinding;

        public HistoryViewHolder(ItemHistorySearchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setBinding(Context context, Track search) {
            mBinding.setSearch(search);
            mBinding.setHanlderClick(new SearchHandlerClick(context, mSearches, mViewModel));
            mBinding.executePendingBindings();
        }
    }
}
