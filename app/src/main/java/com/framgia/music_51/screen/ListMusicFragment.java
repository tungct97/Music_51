package com.framgia.music_51.screen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.databinding.FragmentListMusicBinding;

public class ListMusicFragment extends Fragment {
    private FragmentListMusicBinding mBinding;

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
}
