package com.aji.newsweather.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class HttpUtil<T>
{
    protected Retrofit retrofit;
    private static HttpUtil httpUtil;
    private CompositeSubscription mSubscriptions;
    private Subscription subscription;

    public HttpUtil()
    {
        createRetrofit();
    }

    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    private void createRetrofit()
    {
        retrofit = new Retrofit.Builder().baseUrl(IpService.HOST)
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(client)
                                        .build();
        mSubscriptions = new CompositeSubscription();
    }

    public static HttpUtil getInstance()
    {
        if (httpUtil == null)
        {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    public void getPost(Observable<HttpResult<T>> t , final LoadTasksCallBack loadTasksCallBack)
    {
        subscription = t.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<HttpResult<T>>()
        {
            @Override
            public void onCompleted()
            {
                loadTasksCallBack.onFinish();
            }

            @Override
            public void onError(Throwable e)
            {
                loadTasksCallBack.onFailed();
            }

            @Override
            public void onNext(HttpResult<T> tHttpResult)
            {
                loadTasksCallBack.onSuccess(tHttpResult);
            }

            @Override
            public void onStart()
            {
                loadTasksCallBack.onStart();
            }
        });

        subscribe();
    }

    private void subscribe()
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
