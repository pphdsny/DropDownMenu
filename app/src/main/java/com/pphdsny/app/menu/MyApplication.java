package com.pphdsny.app.menu;

import android.app.Application;
import android.content.Context;

/**
 * Created by wangpeng on 2018/8/6.
 */
public class MyApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
