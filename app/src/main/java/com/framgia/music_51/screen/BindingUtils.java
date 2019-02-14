package com.framgia.music_51.screen;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_51.R;

public class BindingUtils {

    public static String splitGenre(String text) {
        String substring = text.substring(text.lastIndexOf(Utils.COLON) + 1);
        return substring.toUpperCase();
    }

    @BindingAdapter("imageView")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).apply(new RequestOptions()
                .placeholder(R.drawable.image_default))
                .into(view);
    }

    @BindingAdapter("imgResource")
    public static void loadImage(ImageView view, @DrawableRes int res) {
        view.setImageResource(res);
    }
}
