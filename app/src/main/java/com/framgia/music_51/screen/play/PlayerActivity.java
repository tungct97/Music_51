package com.framgia.music_51.screen.play;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {
    private ActivityPlayerBinding mBinding;

    public static Intent getIntent(Context context, Track track) {
        return new Intent(context, PlayerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
    }
}
