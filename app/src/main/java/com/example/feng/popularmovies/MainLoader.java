package com.example.feng.popularmovies;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by feng on 2017/9/17.
 */

public class MainLoader extends AsyncTaskLoader<List<Movie>> {
    private final String mUrl;
    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_LANGUAGE = "language";
    public static final String ZH = "zh";
    private static final String API_KEY = "63190423708f521c776f6807ff3aad7d";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/";

    public MainLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        Uri uri = Uri.parse(mUrl).buildUpon()
                .appendQueryParameter(KEY_API_KEY, API_KEY)
                .appendQueryParameter(KEY_LANGUAGE, ZH)
                .build();
        HttpURLConnection conn = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return convert(result);
    }

    /**
     *
     * @param result json
     * @return 转化的Movie 集合
     */
    private List<Movie> convert(String result) {
        if (result == null) return null;
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject ob = new JSONObject(result);
            JSONArray list = ob.getJSONArray(HttpParams.Params.getKeyResults());
            for (int i = 0; i < list.length(); i++) {
                JSONObject data = list.getJSONObject(i);
                Movie movie = new Movie();
                movie.setTitle(data.getString(HttpParams.Params.getKeyTitle()));
                movie.setPoster(BASE_URL_IMAGE + data.getString(HttpParams.Params.getKeyPoster()));
                movie.setPopularity(data.getDouble(HttpParams.Params.getKeyPopular()));
                movie.setVoteAverage(data.getDouble(HttpParams.Params.getKeyVote()));
                movie.setDesc(data.getString(HttpParams.Params.getKeyDesc()));
                movie.setReleaseDate(data.getString(HttpParams.Params.getKeyReleaseDate()));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;

    }
}
