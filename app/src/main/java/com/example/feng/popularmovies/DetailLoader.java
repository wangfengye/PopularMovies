package com.example.feng.popularmovies;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fengye on 2017/9/20.
 * email 1040441325@qq.com
 */

public class DetailLoader extends AsyncTaskLoader<Movie> {
    public static final String VIDEOS = "/videos";
    public static final String BASE_YOUTUBE= "http://www.youtube.com/watch?v=";
    private final long mId;

    public DetailLoader(Context context, long id) {
        super(context);
        mId =id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Movie loadInBackground() {
        //获取运行时间
        String runTimeJson =HttpUtil.getResponse(String.valueOf(mId));
        String trailerJson = HttpUtil.getResponse(String.valueOf(mId)+ VIDEOS);
        Long runtime =0L;
        String trailer =null;
        try {
            JSONObject object=new JSONObject(runTimeJson);
            runtime =object.getLong(HttpParams.Params.getKeyRuntime());
            JSONObject trailerObject=new JSONObject(trailerJson);
            JSONArray list = trailerObject.getJSONArray(HttpParams.Params.getKeyResults());
            //只去第一部预告
            if (list.length()>0){
             trailer =BASE_YOUTUBE+list.getJSONObject(0).getString(HttpParams.Params.getKeyTrailer());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Movie movie = new Movie();
        movie.setRuntime(runtime);
        movie.setTrailer(trailer);
        Log.i("TAG", "loadInBackground: "+runTimeJson);
        return  movie;
    }
}
