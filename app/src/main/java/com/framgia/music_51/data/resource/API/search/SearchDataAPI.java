package com.framgia.music_51.data.resource.API.search;

import com.framgia.music_51.data.model.Search;
import com.framgia.music_51.screen.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchDataAPI {
    static String getJSONFromAPI(String urlString) throws IOException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(urlString);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constants.SoundCloud.METHOD_GET);
        httpURLConnection.setConnectTimeout(Constants.SoundCloud.CONNECTION_TIMEOUT);
        httpURLConnection.setReadTimeout(Constants.SoundCloud.READ_TIMEOUT);
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

    static ArrayList<Search> getTrackFromAPI(String jsonString) throws JSONException {
        ArrayList<Search> tracks = new ArrayList<>();
        JSONObject root = new JSONObject(jsonString);
        JSONArray collection = root.getJSONArray(Search.TrackJSON.COLLECTION);
        for (int i = 0; i < collection.length(); i++) {
            JSONObject jsonObject = collection.getJSONObject(i)
                    .getJSONObject(Search.TrackJSON.TRACK);
            Search track = new Search(jsonObject);
            // track.setTypeTrack(TypeTrack.TYPE_ONLINE);
            tracks.add(track);
        }

        return tracks;
    }

    static ArrayList<Search> getTrackSearch(String jsonString) throws JSONException {
        ArrayList<Search> tracks = new ArrayList<>();
        JSONObject root = new JSONObject(jsonString);
        JSONArray collection = root.getJSONArray(Search.TrackJSON.COLLECTION);
        for (int i = 0; i < collection.length(); i++) {
            JSONObject jsonObject = collection.getJSONObject(i);
            Search track = new Search(jsonObject);
            //    track.setTypeTrack(TypeTrack.TYPE_ONLINE);
            tracks.add(track);
        }
        return tracks;
    }
}
