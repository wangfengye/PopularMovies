package com.example.feng.popularmovies;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by feng on 2017/9/17.
 */

public class MainLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/";
    public static final String URL_POPULAR = "popular";
    public static final String URL_TOP_RATED = "top_rated";

    public MainLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
      String result =HttpUtil.getResponse(URL_POPULAR);
        return convert(result);
    }

    /**
     *
     * @param result json
     * @return 转化的Movie 集合
     */
    private List<Movie> convert(String result) {
        if (result == null||result.length()==0) return null;
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject ob = new JSONObject(result);
            JSONArray list = ob.getJSONArray(HttpParams.Params.getKeyResults());
            for (int i = 0; i < list.length(); i++) {
                JSONObject data = list.getJSONObject(i);
                Movie movie = new Movie();
                movie.setId(data.getLong(HttpParams.Params.getKeyId()));
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
