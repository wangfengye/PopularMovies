package com.example.feng.popularmovies;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by fengye on 2017/9/20.
 * email 1040441325@qq.com
 */

public class CacheLoader extends AsyncTaskLoader<CacheData> {
    private SetCache mSetCache;
    private GetCache mGetCache;
    private Update mUpdate;
    public CacheLoader(Context context,SetCache setCache) {
        super(context);
        this.mSetCache =setCache;
    }
    public CacheLoader(Context context,GetCache getCache) {
        super(context);
        this.mGetCache =getCache;
    }
    public CacheLoader(Context context,Update update) {
        super(context);
        this.mUpdate =update;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public CacheData loadInBackground() {
        CacheData cacheData =null;
        if (mSetCache!=null){
            Boolean inert=mSetCache.onSetCache();
            cacheData=new CacheData(CacheData.TYPE_INSERT,inert);
            return cacheData;
        }
        if (mGetCache!=null){
            List<Movie> movies=mGetCache.onGetCache();
            cacheData=new CacheData(CacheData.TYPE_QUERY,movies);
            return cacheData;
        }
        if (mUpdate!=null){
            Boolean update= mUpdate.onUpdate();
            cacheData= new CacheData(CacheData.TYPE_UPDATE,update);
            return cacheData;
        }
        return null;
    }
    public interface SetCache{
        boolean onSetCache();
    }
    public interface GetCache{
        List<Movie> onGetCache();
    }
    public interface  Update{
        boolean onUpdate();
    }
}
