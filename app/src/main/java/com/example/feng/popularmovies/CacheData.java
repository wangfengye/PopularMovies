package com.example.feng.popularmovies;

import java.util.List;

/**
 * Created by fengye on 2017/9/20.
 * email 1040441325@qq.com
 * 缓存loader 的返回结果
 */

public class CacheData {
    public static final int TYPE_INSERT=0;
    public static final int TYPE_QUERY=1;
    public static final int TYPE_UPDATE=2;
    private int type;//返回类型
    private Boolean mBoolean;//执行成功与否
    private List<Movie>  mMovies;//查询请求的结果

    public CacheData(int type, Boolean aBoolean) {
        this.type = type;
        mBoolean = aBoolean;
    }

    public CacheData(int type,List<Movie> movies) {
        this.type = type;
        mMovies = movies;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getBoolean() {
        return mBoolean;
    }

    public void setBoolean(Boolean aBoolean) {
        mBoolean = aBoolean;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }
}
