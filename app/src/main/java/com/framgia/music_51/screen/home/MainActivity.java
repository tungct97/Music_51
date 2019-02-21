package com.framgia.music_51.screen.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.framgia.music_51.R;
import com.framgia.music_51.databinding.ActivityMainBinding;
import com.framgia.music_51.screen.ListMusicFragment;
import com.framgia.music_51.screen.person.PersonFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addFragment(HomeFragment.TAG);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String selectedTag;
        switch (item.getItemId()) {
            default:
            case R.id.nav_home:
                selectedTag = HomeFragment.TAG;
                break;
            case R.id.nav_list:
                selectedTag = ListMusicFragment.TAG;
                break;
            case R.id.nav_person:
                selectedTag = PersonFragment.TAG;
                break;
        }
        addFragment(selectedTag);
        return true;
    }

    private void addFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
            return;
        }
        addFragment(getFragment(tag), tag);
    }

    private Fragment getFragment(String tag) {
        Fragment fragment = null;
        switch (tag) {
            case HomeFragment.TAG:
                fragment = HomeFragment.newInstance();
                break;
            case ListMusicFragment.TAG:
                fragment = ListMusicFragment.newInstance();
                break;
            case PersonFragment.TAG:
                fragment = PersonFragment.newInstance();
                break;
        }
        return fragment;
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment, tag).commit();
    }
}
