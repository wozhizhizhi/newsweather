package com.aji.newsweather.net;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:zhangmiss on 2018/02/02 0002.
 * mail:867596152@qq.com
 * Descripiton:
 */

public abstract class ResultCallback
{
    public abstract void onError(Request request , Exception e);
    public abstract void onResponse(Response response) throws IOException;
}
