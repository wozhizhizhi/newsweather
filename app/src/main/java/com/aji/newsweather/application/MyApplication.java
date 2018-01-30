package com.aji.newsweather.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class MyApplication extends Application
{
    private static MyApplication intance;
    public static Context context;

    public List<AppCompatActivity> activitys = new LinkedList<>();
    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }

    public static MyApplication getInstance()
    {
        return intance;
    }

    public static Context getContext()
    {
        return context;
    }

    public void addActivity(AppCompatActivity activity)
    {
        activitys.add(activity);
    }

    public void removeActivity(AppCompatActivity activity)
    {
        activitys.remove(activity);
    }

    public void closeApplication()
    {
        closeActivity();
    }

    // 关闭所有的activity
    public void closeActivity()
    {
        ListIterator<AppCompatActivity> listIterator = activitys.listIterator();
        while (listIterator.hasNext())
        {
            AppCompatActivity activity = listIterator.next();
            if (activity != null)
            {
                activity.finish();
            }
        }
    }
}
