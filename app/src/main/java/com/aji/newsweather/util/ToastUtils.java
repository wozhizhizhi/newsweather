package com.aji.newsweather.util;

import android.content.Context;
import android.widget.Toast;

import com.aji.newsweather.R;
import com.aji.newsweather.application.MyApplication;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ToastUtils {
    private ToastUtils() {
    }

    public static void showString(Context context, String content) {
        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }


    public static void showString(String content) {
        Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void showInt(Context context, int resId) {
        Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showNetError(Context context) {
        Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
    }

    public static void showNetError() {
        Toast.makeText(MyApplication.getContext(), MyApplication.getContext().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
    }

    public static void showNomore(Context context) {
        Toast.makeText(context.getApplicationContext(), context.getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
    }

    public static void showNextDO(Context context) {
        Toast.makeText(context.getApplicationContext(), context.getString(R.string.now_not_done), Toast.LENGTH_SHORT).show();
    }
    public static void showServiceError(Context context) {
        Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getString(R.string.service_error), Toast.LENGTH_SHORT).show();
    }

}
