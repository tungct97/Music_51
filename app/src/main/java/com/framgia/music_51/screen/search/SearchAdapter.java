package com.framgia.music_51.screen.search;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.databinding.ItemListSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    public List<Search> mTracks;
    private List<Search> mTracksFull;
    private ValueFilter mValueFilter;

    public SearchAdapter() {
        mTracks = new ArrayList<>();
        mTracksFull = new ArrayList<>();
    }

    public void setData(List<Search> searches) {
        if (searches == null) {
            return;
        }
        mTracks.addAll(searches);
        notifyItemRangeChanged(mTracks.size(), searches.size());
    }


    @Override
    public Filter getFilter() {
        if (mValueFilter == null) {
            mValueFilter = new ValueFilter();
        }
        return mValueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Search> filterList = new ArrayList<>();
                for (int i = 0; i < mTracksFull.size(); i++) {
                    if ((mTracksFull.get(i).getTitle().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mTracksFull.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mTracksFull.size();
                results.values = mTracksFull;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mTracks = (List<Search>) results.values;
            notifyDataSetChanged();
        }

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemListSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_list_search, viewGroup, false);
        binding.textTrack.setText(mTracks.get(i).getTitle());
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.setBinding(mTracks.get(i));
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private ItemListSearchBinding mBinding;

        public SearchViewHolder(ItemListSearchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void setBinding(Search search) {
            mBinding.setSearch(search);
        }
    }
}
