package com.example.feng.popularmovies;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;


/**
 * Created by feng on 2017/9/17.
 */

public class MainLoader extends AsyncTaskLoader<List<Movie>> {


    public MainLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
      String result =HttpUtil.getResponse(HttpUtil.URL_POPULAR);
        return HttpUtil.convert(result);
    }


}
