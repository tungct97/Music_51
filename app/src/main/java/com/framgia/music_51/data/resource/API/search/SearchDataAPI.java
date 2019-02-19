package com.framgia.music_51.data.resource.API.search;

import android.provider.SyncStateContract;

import com.framgia.music_51.data.model.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchDataAPI {

    public String getSearch(String path) throws IOException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(path);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        String jsonString = stringBuilder.toString();
        httpURLConnection.disconnect();
        return jsonString;
    }

    public static ArrayList<Search> getSearchData(String json) throws JSONException {
        ArrayList<Search> searches = new ArrayList<>();
        JSONObject root = new JSONObject(json);
        JSONArray collection = root.getJSONArray(Search.JSONKey.COLLECTION);
        for (int i = 0; i < collection.length(); i++) {
            JSONObject jsonObject = collection.getJSONObject(i);
            Search search = new Search(jsonObject);
            searches.add(search);
        }
        return searches;
    }
}
