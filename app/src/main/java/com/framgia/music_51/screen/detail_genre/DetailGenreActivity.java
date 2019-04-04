package com.framgia.music_51.screen.detail_genre;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.databinding.ActivityDetalGenreBinding;
import com.framgia.music_51.screen.BindingUtils;
import com.framgia.music_51.screen.Utils;


public class DetailGenreActivity extends AppCompatActivity {
    private static final String EXTRA_GENRE = "EXTRA_GENRE";
    private static final String BUNDLE_GENRE = "BUNDLE_GENRE";
    private static final int INDEX = 0;
    private static final int PER_PAGE = 10;
    private DetailAdapter mAdapter;
    private MusicResponse mGenre;
    private int mOffset;
    private ActivityDetalGenreBinding mBinding;
    private DetailGenreViewModel mViewModel;
    private EnlessScrollListener mListener;
    private boolean mIsFinish = false;

    public static Intent getIntent(Context context, MusicResponse genres) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_GENRE, genres);
        Intent intent = new Intent(context, DetailGenreActivity.class);
        intent.putExtra(BUNDLE_GENRE, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detal_genre);
        mViewModel = ViewModelProviders.of(DetailGenreActivity.this).get(DetailGenreViewModel.class);
        mAdapter = new DetailAdapter(this);
        getGenre(getMusicGenreIntent());
        getData();
        mListener = new EnlessScrollListener(new LinearLayoutManager(this)) {
            @Override
            public void onLoadMore() {
                loadData();
            }
        };
        mBinding.recyclerDetailGenre.addOnScrollListener(mListener);
        mBinding.recyclerDetailGenre.setAdapter(mAdapter);
        mBinding.recyclerDetailGenre.setLayoutManager(new LinearLayoutManager(this));
        initToolbar();
    }

    private void getData() {
        mBinding.titlePlaylist.setText(BindingUtils.splitGenre(mGenre.getGenre()));
        mBinding.textNumberPlaylist.setText("100 songs");
        mViewModel.getTracks(Utils.KIND, mGenre.getGenre(), mOffset).observe((LifecycleOwner) this, new Observer<MusicResponse>() {
            @Override
            public void onChanged(@Nullable MusicResponse musicResponse) {
                if (musicResponse.getTracks().size() == 0) {
                    mIsFinish = true;
                    return;
                }

                mAdapter.removeLoadingIndicator();
                mAdapter.setData(musicResponse.getTracks());
                Glide.with(DetailGenreActivity.this)
                        .load(musicResponse.getCollections().get(INDEX).getTrack().getArtworkUrl())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.image_default)).into(mBinding.ivPlaylist);
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Bundle getMusicGenreIntent() {
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_GENRE);
        return bundle;
    }

    private MusicResponse getGenre(Bundle bundle) {
        mGenre = bundle.getParcelable(EXTRA_GENRE);
        return mGenre;
    }

    private void loadData() {
        if (mIsFinish) {
            return;
        }
        mAdapter.addLoadingIndicator();
        mOffset += PER_PAGE;
        mViewModel.getTracks(Utils.KIND, mGenre.getGenre(), mOffset);
    }
}
