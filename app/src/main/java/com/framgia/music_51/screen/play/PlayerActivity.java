package com.framgia.music_51.screen.play;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.framgia.music_51.BlurBuilder;
import com.framgia.music_51.R;
import com.framgia.music_51.data.model.Track;
import com.framgia.music_51.databinding.ActivityPlayerBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PlayerActivity extends AppCompatActivity {
    private static final String EXTRA_TRACK = "EXTRA_TRACK";
    private static final String BUNDLE_TRACK = "BUNDLE_TRACK";
    private ActivityPlayerBinding mBinding;
    private Track mTrack;

    public static Intent getIntent(Context context, Track track) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_TRACK, track);
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(BUNDLE_TRACK, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        initToolbar();
        initAnim();
        getTrack(getTrackIntent());
        initViewModel();
        displayBackgroundImage();
    }

    private void initAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        mBinding.imageMusicPlayer.startAnimation(animation);
    }

    private void initViewModel() {
        PlayerViewModel viewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        viewModel.setData(mTrack);
        mBinding.setViewModel(viewModel);
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbarPlayer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbarPlayer.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Bundle getTrackIntent() {
        Bundle bundle = getIntent().getBundleExtra(BUNDLE_TRACK);
        return bundle;
    }

    private Track getTrack(Bundle bundle) {
        mTrack = bundle.getParcelable(EXTRA_TRACK);
        return mTrack;
    }

    private void displayBackgroundImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            BlurBuilder.getBimap(mTrack.getArtworkUrl(), this).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).subscribe(new Consumer<Bitmap>() {
                @Override
                public void accept(Bitmap bitmap) throws Exception {
                    Bitmap blurBitmap = BlurBuilder.blur(getApplicationContext(), bitmap);
                    Glide.with(getApplicationContext()).load(blurBitmap).into(mBinding.imagePlayer);
                }
            });
        } else {
            Glide.with(getApplicationContext()).load(mTrack.getArtworkUrl()).into(mBinding.imagePlayer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_person:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
