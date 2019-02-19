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
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Search;
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
        initView();
        initViewModel();
    }

    private void initView() {
        mAdapter = new SearchAdapter();
        mHistoryAdapter = new HistoryAdapter();
        mBinding.recyclerHistory.setAdapter(mHistoryAdapter);
        mBinding.recyclerSearch.setAdapter(mAdapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        getData();
    }

    private void getData() {
        mViewModel.getQuery(20).observe(this, new Observer<List<Search>>() {
            @Override
            public void onChanged(@Nullable List<Search> searches) {
                mBinding.searchView.setActivated(true);
                mBinding.searchView.setQueryHint("Type your keyword here");
                mBinding.searchView.onActionViewExpanded();
                mBinding.searchView.setIconified(false);
                mBinding.searchView.clearFocus();
                mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        mAdapter.getFilter().filter(s);
                        mAdapter.setData(searches);
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
