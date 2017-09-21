package com.example.feng.popularmovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by fengye on 2017/9/21.
 * email 1040441325@qq.com
 * 此服务提供给SyncAdapter framework，用于调用Authenticator的方法。
 * 在onCreat()中创建Authenticator对象，在onBind()中返回一个binder对象用于在Authenticator和framework间传输数据
 */

public class AuthenticatorService extends Service{
    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
