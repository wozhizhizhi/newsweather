package com.aji.newsweather.application;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class MyApplication extends Application
{
    private static volatile MyApplication intance;
    @Override
    public void onCreate()
    {
        super.onCreate();
        LitePal.initialize(this);
    }

    private static MyApplication getIntance()
    {
        synchronized (MyApplication.class)
        {
            if (intance == null)
            {
                intance = new MyApplication();
            }
            return intance;
        }
    }
}
