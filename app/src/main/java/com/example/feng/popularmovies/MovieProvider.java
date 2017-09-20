package com.example.feng.popularmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fengye on 2017/9/19.
 * email 1040441325@qq.com
 */

public class MovieProvider extends ContentProvider{
    private SQLiteDatabase mDatabase;
    public static final Uri URI =Uri.parse("content://com.example.feng.popularmovies/movie");

    @Override
    public boolean onCreate() {
        mDatabase =new DbHelper(getContext()).getWritableDatabase();
        return false;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
      return mDatabase.query(DbHelper.MOVIE_TABLE_NAME,strings,s,strings1,null, null, s1, null);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        mDatabase.insert(DbHelper.MOVIE_TABLE_NAME,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int count = mDatabase.delete(DbHelper.MOVIE_TABLE_NAME, s, strings);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String s, @Nullable String[] strings) {
        int row =mDatabase.update(DbHelper.MOVIE_TABLE_NAME,values,s,strings);
        if (row>0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        Log.i("tag", "update: "+row);
        return row;
    }
}
