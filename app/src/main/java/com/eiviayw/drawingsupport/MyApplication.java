package com.eiviayw.drawingsupport;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication mInstance = null;

    public static MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }
}
