package com.framgia.music_51.data.resource.API.search;

import android.os.AsyncTask;

import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.model.Search;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class SearchAsysTask extends AsyncTask<String, Void, ArrayList<Search>> {
    private Callback mCallBack;
    private Exception mException;
    private String mSearchKey;

    public SearchAsysTask(Callback callBack) {
        mCallBack = callBack;
    }

    @Override
    protected ArrayList<Search> doInBackground(String... strings) {
        ArrayList<Search> tracks = new ArrayList<>();
        try {
            String json = SearchDataAPI.getJSONFromAPI(strings[0]);
            tracks = SearchDataAPI.getTrackSearch(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            mException = e;
        }
        return tracks;
    }

    @Override
    protected void onPostExecute(ArrayList<Search> tracks) {
        super.onPostExecute(tracks);
        if (mCallBack == null) {
            return;
        }
        if (mException == null) {
            mCallBack.getDataSuccess(tracks);
        } else {
            mCallBack.getDataFail(mException);
        }
    }
}