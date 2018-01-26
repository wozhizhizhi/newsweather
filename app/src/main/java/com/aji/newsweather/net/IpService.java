package com.aji.newsweather.net;

import android.database.Observable;

import com.aji.newsweather.db.Province;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public interface IpService
{
    static final String HOST = "http://guolin.tech/api";

    // 请求返回所有的省份
    @FormUrlEncoded
    @POST("/china")
    Observable<HttpResult<Province>> getProvince();
}
