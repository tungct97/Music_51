package com.framgia.music_51.data.resource.API.search;

import android.os.AsyncTask;

import com.framgia.music_51.data.Callback;
import com.framgia.music_51.data.model.Search;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchAsysTask extends AsyncTask<String, Void, List<Search>> {
    private Callback<List<Search>> mCallBack;
    private Exception mException;

    public SearchAsysTask(Callback<List<Search>> callBack) {
        mCallBack = callBack;
    }

    @Override
    protected List<Search> doInBackground(String... strings) {
        List<Search> searches = new ArrayList<>();
        try {
            SearchDataAPI movieDataAPI = new SearchDataAPI();
            String json = movieDataAPI.getSearch(strings[0]);
            searches = movieDataAPI.getSearchData(json);
        } catch (JSONException e) {
            e.printStackTrace();
            mException = e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searches;
    }

    @Override
    protected void onPostExecute(List<Search> searches) {
        super.onPostExecute(searches);
        if (mCallBack == null) {
            return;
        }
        if (mException == null) {
            mCallBack.getDataSuccess(searches);
        } else {
            mCallBack.getDataFail(mException);
        }
    }
}