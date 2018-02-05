package com.aji.newsweather.util;

import android.text.TextUtils;
import com.aji.newsweather.db.City;
import com.aji.newsweather.db.County;
import com.aji.newsweather.db.Province;
import com.aji.newsweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author:zhangmiss on 2018/02/02 0002.
 * mail:867596152@qq.com
 * Descripiton:处理返回的数据
 */

public class JsonUtil
{
    // 处理返回的省数据
    public static boolean handleProviceData(String response)
    {
       if (!TextUtils.isEmpty(response))
       {
           try
           {
               JSONArray allProvinces = new JSONArray(response);
               for (int i = 0 ; i < allProvinces.length() ; ++i)
               {
                   JSONObject provinceObject = allProvinces.getJSONObject(i);
                   Province province = new Province();
                   province.setProvinceCode(provinceObject.getInt("id"));
                   province.setProvinceName(provinceObject.getString("name"));
                   province.save();
               }
               return true;
           }
           catch (JSONException e)
           {
                e.printStackTrace();
           }
       }
        return false;
    }

    // 处理返回的市数据
    public static boolean handleCityData(String response , int provinceId)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allcitys = new JSONArray(response);
                for (int i = 0 ; i < allcitys.length() ; ++i)
                {
                    JSONObject cityObject = allcitys.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 处理返回的区数据
    public static boolean handleCountyData(String response , int cityId)
    {
        if (!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0 ; i < allCounty.length() ; ++i)
                {
                    JSONObject countyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
