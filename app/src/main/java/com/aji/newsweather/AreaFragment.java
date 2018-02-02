package com.aji.newsweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aji.newsweather.base.BaseFragment;
import com.aji.newsweather.net.HttpUtil;
import com.aji.newsweather.net.IpService;
import com.aji.newsweather.test.City;
import com.aji.newsweather.test.County;
import com.aji.newsweather.test.Province;
import com.aji.newsweather.weight.MyTitle;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author:zhangmiss on 2018/01/30 0030.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class AreaFragment extends BaseFragment
{

    @BindView(R.id.fragment_mytitle_id)
    MyTitle fragmentMytitleId;
    @BindView(R.id.fragment_listview_id)
    ListView fragmentListviewId;
    private HttpUtil httpUtil;
    private List<Province> provinces = new ArrayList<>();
    private List<String> dataList = new ArrayList<>();

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ArrayAdapter<String> adapter;

    /**
     * 市列表
     */
    private List<City> cityList;

    /**
     * 县列表
     */
    private List<County> countyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince;

    /**
     * 选中的城市
     */
    private City selectedCity;

    /**
     * 当前选中的级别
     */
    private int currentLevel;

    public static AreaFragment newInstance()
    {
        return new AreaFragment();
    }

    @Override
    public int getLayoutRes()
    {
        return R.layout.choose_areatest;
    }

    @Override
    public void initView()
    {
        fragmentMytitleId.setLeftVisibility(1);
        fragmentMytitleId.setRightVisibility(1);
        httpUtil = HttpUtil.getInstance();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        fragmentListviewId.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        fragmentListviewId.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinces.get(position);
                    queryCities();
                }
                else if (currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }

    private void getFromServerProvince()
    {
        IpService ipService = httpUtil.retrofit.create(IpService.class);
        Subscription subscription = ipService.getProvince("china").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Province>>()
        {
            @Override
            public void onCompleted()
            {
                hideProgress();
            }

            @Override
            public void onError(Throwable e)
            {
                hideProgress();
                e.printStackTrace();
                showToast("网络加载失败！" + e);
            }

            @Override
            public void onNext(List<Province> provinces)
            {
                hideProgress();
                provinces = provinces;
                dataList.clear();
                for (Province province : provinces)
                {
                    dataList.add(province.getName());
                    province.save();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onStart()
            {
                showProgress("正在加载网络请稍后...");
            }
        });
        httpUtil.subscribe(subscription);
    }

    private void getFromServerCity(final int code)
    {
        IpService ipService = httpUtil.retrofit.create(IpService.class);
        Subscription subscription = ipService.getCity("china/"+ code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<City>>()
                {
                    @Override
                    public void onCompleted()
                    {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        hideProgress();
                        e.printStackTrace();
                        showToast("网络加载失败！" + e);
                    }

                    @Override
                    public void onNext(List<City> citys)
                    {
                        hideProgress();
                        cityList = citys;
                        dataList.clear();
                        for (City city : citys)
                        {
                            dataList.add(city.getName());
//                            city.setProvinceId(code);
//                            city.save();
                        }
                        adapter.notifyDataSetChanged();
                        fragmentListviewId.setSelection(0);
                        currentLevel = LEVEL_CITY;
                    }

                    @Override
                    public void onStart()
                    {
                        showProgress("正在加载网络请稍后...");
                    }
                });
        httpUtil.subscribe(subscription);
    }

    private void getFromServerCounty(final int code , int cityCode)
    {
        IpService ipService = httpUtil.retrofit.create(IpService.class);
        Subscription subscription = ipService.getCounty("china/"+ code + "/"+ cityCode).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<County>>()
                {
                    @Override
                    public void onCompleted()
                    {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        hideProgress();
                        e.printStackTrace();
                        showToast("网络加载失败！" + e);
                    }

                    @Override
                    public void onNext(List<County> countys)
                    {
                        hideProgress();
                        countyList = countys;
                        dataList.clear();
                        for (County county : countys)
                        {
                            dataList.add(county.getName());
//                            city.setProvinceId(code);
//                            city.save();
                        }
                        adapter.notifyDataSetChanged();
                        fragmentListviewId.setSelection(0);
                        currentLevel = LEVEL_COUNTY;
                    }

                    @Override
                    public void onStart()
                    {
                        showProgress("正在加载网络请稍后...");
                    }
                });
        httpUtil.subscribe(subscription);
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryProvinces()
    {
        fragmentMytitleId.setTitle("中国");
        provinces = DataSupport.findAll(Province.class);
        if (provinces.size() > 0)
        {
            dataList.clear();
            for (Province province : provinces)
            {
                dataList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            fragmentListviewId.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            getFromServerProvince();
        }
    }

    /**
     * 查询选中省内所有的市，去服务器上查询(为了简单我去掉了数据库查询)
     */
    private void queryCities()
    {
        fragmentMytitleId.setTitle(selectedProvince.getName());
//        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
//        if (cityList.size() > 0)
//        {
//            dataList.clear();
//            for (City city : cityList)
//            {
//                dataList.add(city.getName());
//            }
//            adapter.notifyDataSetChanged();
//            fragmentListviewId.setSelection(0);
//            currentLevel = LEVEL_CITY;
//        } else
//            {
            int code = selectedProvince.getId();
            getFromServerCity(code);

//        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCounties() {
        fragmentMytitleId.setTitle(selectedCity.getName());
//        backButton.setVisibility(View.VISIBLE);
//        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
//        if (countyList.size() > 0) {
//            dataList.clear();
//            for (County county : countyList) {
//                dataList.add(county.getCountyName());
//            }
//            adapter.notifyDataSetChanged();
//            listView.setSelection(0);
//            currentLevel = LEVEL_COUNTY;
//        } else {
            int provinceCode = selectedProvince.getId();
            int cityCode = selectedCity.getId();
            getFromServerCounty(provinceCode , cityCode);
//        }
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        httpUtil.unsubscribe();
    }
}
