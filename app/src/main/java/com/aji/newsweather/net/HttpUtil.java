package com.aji.newsweather.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class HttpUtil<T>
{
    private Retrofit retrofit;
    private static HttpUtil httpUtil;
    private CompositeSubscription mSubscriptions;
    private Subscription subscription;

    public HttpUtil()
    {
        createRetrofit();
    }

    private void createRetrofit()
    {
        retrofit = new Retrofit.Builder().baseUrl(IpService.HOST)
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        mSubscriptions = new CompositeSubscription();
    }

    public static HttpUtil getHttpUtil()
    {
        if (httpUtil == null)
        {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    private void getPost(Observable<HttpResult<T>> t , LoadTasksCallBack loadTasksCallBack)
    {
        IpService ipService = retrofit.create(IpService.class);


    }

    public void subscribe()
    {
        if(subscription!=null)
        {
            mSubscriptions.add(subscription);
        }
    }

    public void unsubscribe()
    {
        if (mSubscriptions != null && mSubscriptions.hasSubscriptions())
        {
            mSubscriptions.unsubscribe();
        }
    }
}
