package com.example.foundu;

import android.app.Application;
import android.content.Context;

public class FoundUApplication extends Application {
    private static FoundUApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public synchronized static FoundUApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}