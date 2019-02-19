package com.framgia.music_51.screen.person;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.FragmentPersonBinding;

import java.util.List;

import retrofit2.http.HEAD;

public class PersonFragment extends Fragment implements PersonAdapter.PersonHanlderClick {
    public static final String TAG = "PersonFragment";
    private FragmentPersonBinding mBinding;
    private PersonAdapter mAdapter;
    private PersonViewModel mViewModel;

    public static PersonFragment newInstance() {
        return new PersonFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_person, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView() {
        mAdapter = new PersonAdapter(this);
        mBinding.recyclerFavourite.setAdapter(mAdapter);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(PersonViewModel.class);
        setData();
    }

    private void setData() {
        mViewModel.getFavourites().observe(getActivity(), new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable List<Track> tracks) {
                mAdapter.setData(tracks);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.onDestroy();
    }

    @Override
    public void removeFavourite(final Track track) {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.title_question)
                .setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mViewModel.removeItemFavourite(track);
                    }
                })
                .setNegativeButton(R.string.title_no, null)
                .show();
    }
}
