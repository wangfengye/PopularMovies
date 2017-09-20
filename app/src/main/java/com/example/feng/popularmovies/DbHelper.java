package com.example.feng.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fengye on 2017/9/19.
 * email 1040441325@qq.com
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String DATA_BASE_NAME = "cache.db";
    private static final int DATA_BASE_VERSION=1;
    public static final String MOVIE_TABLE_NAME="movie";
    private final String CREATE_TABLE_MOVIE="create table "+MOVIE_TABLE_NAME
            +"("+HttpParams.Params.getKeyId()+" integer primary key "//on conflict replace"
            +","+HttpParams.Params.getKeyTitle()+" text"
            +","+HttpParams.Params.getKeyPoster()+" text"
            +","+HttpParams.Params.getKeyDesc()+" text"
            +","+HttpParams.Params.getKeyPopular()+" text"
            +","+HttpParams.Params.getKeyVote()+" text"
            +","+HttpParams.Params.getKeyReleaseDate()+" text"
            +","+HttpParams.Params.getKeyCollection()+" text"
            +")";
    public DbHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
