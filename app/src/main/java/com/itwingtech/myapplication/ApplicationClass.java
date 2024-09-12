package com.itwingtech.myapplication;

import android.app.Application;

import com.itwingtech.myawesomeads.MyAwesomeAds;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyAwesomeAds.initApp(this);
    }
}
