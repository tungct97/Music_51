package com.framgia.music_51.screen.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Collection;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private int mCurrentPage = 0;
    private final long DELAY_MS = 2000;
    private final long PERIOD_MS = 3000;
    private FragmentHomeBinding mBinding;
    private HomeViewModel mViewModel;
    private GenreAdapter mGenreAdapter;
    private ImagePagerAdapter mImagePagerAdapter;
    private HandlerClick mHandlerClick;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        mBinding.recyclerGenre.setAdapter(mGenreAdapter);
        mBinding.setHandlerClick(mHandlerClick);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        mGenreAdapter = new GenreAdapter(getActivity());
        mHandlerClick = new HandlerClick(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        mViewModel.getGenres().observe(this, new Observer<List<MusicResponse>>() {
            @Override
            public void onChanged(@Nullable List<MusicResponse> genres) {
                mGenreAdapter.setData(genres);
                mImagePagerAdapter = new ImagePagerAdapter(genres, getActivity());
                mGenreAdapter.notifyDataSetChanged();
            }
        });
    }
}
