package com.framgia.music_51.screen.offline;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.FragmentListMusicBinding;

import java.util.List;

public class ListMusicFragment extends Fragment {
    public static final String TAG = "ListMusicFragment";
    private FragmentListMusicBinding mBinding;
    private OfflineAdapter mAdapter;
    private OfflineViewModel mViewModel;

    public static ListMusicFragment newInstance() {
        return new ListMusicFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_list_music, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initView();
        initViewModel();
    }

    private void initToolbar() {
    }

    private void initView() {
        mAdapter = new OfflineAdapter(getActivity());
        mBinding.recyclerPlaylist.setAdapter(mAdapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(OfflineViewModel.class);
        getData();
    }

    private void getData() {
        mViewModel.getDownloads().observe(getActivity(), new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                mAdapter.setData(tracks);
            }
        });
    }
}
