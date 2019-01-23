package com.framgia.music_51.screen.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.MusicResponse;
import com.framgia.music_51.databinding.ItemPagerBinding;


import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private ItemPagerBinding mBinding;
    private List<MusicResponse> mMusicResponses;
    private MusicResponse mMusicsResponse;
    private Context mContext;

    public ImagePagerAdapter(List<MusicResponse> musicResponses, Context context) {
        mMusicResponses = musicResponses;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMusicResponses != null ? mMusicResponses.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_pager, container, false);
        mMusicsResponse = mMusicResponses.get(position);
        mBinding.imagePager.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = mMusicsResponse.getCollections().get(position).getTrack().getArtworkUrl();
        Glide.with(mContext).
                load(url)
                .into(mBinding.imagePager);
        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
