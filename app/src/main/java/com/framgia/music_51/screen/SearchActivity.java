package com.framgia.music_51.screen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.music_51.R;

public class SearchActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
