package com.aji.newsweather.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Author:zhangmiss on 2018/01/30 0030.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class ActivityUtil
{
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId)
    {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
