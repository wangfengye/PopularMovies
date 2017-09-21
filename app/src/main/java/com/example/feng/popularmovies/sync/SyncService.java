package com.example.feng.popularmovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by fengye on 2017/9/21.
 * email 1040441325@qq.com
 */

public class SyncService extends Service{
    private static SyncAdapter mSyncAdapter = null;
    private static final Object mSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        synchronized (mSyncAdapterLock){
            if (mSyncAdapter==null){
                mSyncAdapter= new SyncAdapter(getApplicationContext(),true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSyncAdapter.getSyncAdapterBinder();
    }
}
