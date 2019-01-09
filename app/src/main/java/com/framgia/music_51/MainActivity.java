package com.framgia.music_51;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.framgia.music_51.databinding.ActivityMainBinding;
import com.framgia.music_51.screen.home.HomeFragment;
import com.framgia.music_51.screen.ListMusicFragment;
import com.framgia.music_51.screen.PersonFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                selectedFragment = HomeFragment.newInstance();
                break;
            case R.id.nav_list:
                selectedFragment = ListMusicFragment.newInstance();
                break;
            case R.id.nav_person:
                selectedFragment = PersonFragment.newInstance();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        return true;
    }
}
