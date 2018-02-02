package com.aji.newsweather.test;

import org.litepal.crud.DataSupport;

/**
 * Author:zhangmiss on 2018/01/26 0026.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class City extends DataSupport
{
    private int id;
    private String name;

//    private int provinceId;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

//    public int getProvinceId()
//    {
//        return provinceId;
//    }
//
//    public void setProvinceId(int provinceId)
//    {
//        this.provinceId = provinceId;
//    }

}
