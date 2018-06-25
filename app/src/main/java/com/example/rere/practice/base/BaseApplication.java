package com.example.rere.practice.base;

import android.app.Application;

/**
 * Created by rere on 18-5-15.
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public static BaseApplication getInstance() {
        if (sInstance == null) {
            throw new RuntimeException("IlleagelStateExp : instance is null, application error");
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
