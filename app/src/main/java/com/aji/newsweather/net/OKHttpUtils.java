package com.aji.newsweather.net;

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:zhangmiss on 2018/02/02 0002.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class OKHttpUtils
{
    private static OKHttpUtils mInstance;
    private OkHttpClient mOKHttpClient;
    private Handler mHandler;

    public static OKHttpUtils getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (OKHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OKHttpUtils(context);
                }
            }
        }
        return mInstance;
    }

    private OKHttpUtils(Context context)
    {
        //通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        //通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
//        File sdcache = context.getExternalCacheDir();
//        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder().writeTimeout(10 , TimeUnit.SECONDS)
                                                                .readTimeout(20 , TimeUnit.SECONDS)
                                                                .connectTimeout(15 , TimeUnit.SECONDS);
//                                                                .cache(new Cache(sdcache.getAbsoluteFile() , cacheSize));
        mOKHttpClient = builder.build();
        mHandler = new Handler();

    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * GET请求
     */
    public void getAsynHttp(String url , ResultCallback resultCallback)
    {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOKHttpClient.newCall(request);
        dealResult(call , resultCallback);
    }

    private void dealResult(Call call , final ResultCallback resultCallback)
    {
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                sendFailCallback(call.request() , e , resultCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                sendSuccessCallback(response , resultCallback);
            }
        });

    }

    private void sendSuccessCallback(final Response response , final ResultCallback resultCallback)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    resultCallback.onResponse(response);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendFailCallback(final Request request , final Exception e , final ResultCallback resultCallback)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (resultCallback != null)
                {
                    resultCallback.onError(request , e);
                }
            }
        });
    }


}
