package com.aji.newsweather.net;


import com.aji.newsweather.test.City;
import com.aji.newsweather.test.County;
import com.aji.newsweather.test.Province;

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

    // 请求返回所有的省份的城市
    @GET
    Observable<List<City>> getCity(@Url String url);

    // 请求返回所有的省份的县
    @GET
    Observable<List<County>> getCounty(@Url String url);
}

