package com.framgia.music_51.screen.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivitySearchBinding;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchAdapter mAdapter;
    private HistoryAdapter mHistoryAdapter;
    private SearchViewModel mViewModel;
    private ActivitySearchBinding mBinding;

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        mBinding.setHanldeClick(new SearchHandlerClick(this));
        initViewModel();
        initView();
    }

    private void initView() {
        mAdapter = new SearchAdapter(this, mViewModel);
        mHistoryAdapter = new HistoryAdapter(this, mViewModel);
        mBinding.recyclerHistory.setAdapter(mHistoryAdapter);
        mBinding.recyclerSearch.setAdapter(mAdapter);
        getHistory();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        mBinding.searchView.setQueryHint("Nhập tên bài hát");
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                getData(s);
                return true ;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void getHistory() {
        mViewModel.getHistory().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> searches) {
                mHistoryAdapter.setData(searches);
            }
        });
    }

    private void getData(String s) {
        mViewModel.getTrack(s).observe(this, new Observer<Collection>() {
            @Override
            public void onChanged(@Nullable Collection collection) {
               mAdapter.setData(collection.getCollection());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Log.d("ducanh123", "onQueryTextChange: ");
//                getData(newText);
//
//                return false;
//            }
//        });
        return true;
    }
}
