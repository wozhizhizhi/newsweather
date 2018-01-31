package com.aji.newsweather.net;

import com.aji.newsweather.db.Province;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public interface IpService
{
    static final String HOST = "http://guolin.tech/api/";

    // 请求返回所有的省份
    @GET
    Observable<List<Province>> getProvince(@Url String url);
}
