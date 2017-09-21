package com.example.feng.popularmovies.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.feng.popularmovies.HttpParams;
import com.example.feng.popularmovies.HttpUtil;
import com.example.feng.popularmovies.Movie;
import com.example.feng.popularmovies.MovieProvider;
import com.example.feng.popularmovies.R;

import java.util.List;

/**
 * Created by fengye on 2017/9/21.
 * email 1040441325@qq.com
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter{


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }


    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient client, SyncResult result) {
        List<Movie> movies =HttpUtil.convert(HttpUtil.getResponse(HttpUtil.URL_POPULAR));
        Log.i("ssssss", "onPerformSync: "+movies.size());
        Uri uri = MovieProvider.URI;
        for (Movie movie : movies) {
            ContentValues values = new ContentValues();
            values.put(HttpParams.Params.getKeyId(), movie.getId());
            values.put(HttpParams.Params.getKeyPoster(), movie.getPoster());
            values.put(HttpParams.Params.getKeyTitle(), movie.getTitle());
            values.put(HttpParams.Params.getKeyDesc(), movie.getDesc());
            values.put(HttpParams.Params.getKeyPopular(), movie.getPopularity());
            values.put(HttpParams.Params.getKeyVote(), movie.getVoteAverage());
            values.put(HttpParams.Params.getKeyReleaseDate(), movie.getReleaseDate());
            values.put(HttpParams.Params.getKeyCollection(), movie.getCollection());
            getContext().getContentResolver().insert(uri, values);
        }

    }
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

     /*
      * Add the account and account type, no password or user data
      * If successful, return the Account object, otherwise report an error.
      */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
         /*
          * If you don't set android:syncable="true" in
          * in your <provider> element in the manifest,
          * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
          * here.
          */

        }
        return newAccount;
    }
}
