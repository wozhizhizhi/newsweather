package com.aji.newsweather.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aji.newsweather.R;
import com.aji.newsweather.base.BaseFragment;
import com.aji.newsweather.db.City;
import com.aji.newsweather.db.County;
import com.aji.newsweather.db.Province;
import com.aji.newsweather.net.OKHttpUtils;
import com.aji.newsweather.net.ResultCallback;
import com.aji.newsweather.ui.MainActivity;
import com.aji.newsweather.ui.WeatherActivity;
import com.aji.newsweather.util.JsonUtil;
import com.aji.newsweather.weight.MyTitle;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:zhangmiss on 2018/01/30 0030.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class ChooseAreaFragment extends BaseFragment
{
    @BindView(R.id.choosearea_mytitleId)
    MyTitle chooseareaMytitleId;
    @BindView(R.id.choosearea_listviewId)
    ListView chooseareaListviewId;

    /**
     * 省列表
     */
    private List<Province> provinceList;

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

    private List<String> dataList = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    @Override
    public int getLayoutRes()
    {
        return R.layout.choose_area;
    }

    @Override
    public void initView()
    {
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        chooseareaListviewId.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        chooseareaListviewId.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinceList.get(position);
                    queryCitys();
                }
                else if (currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(position);
                    queryCountys();
                }
                else if (currentLevel == LEVEL_COUNTY)
                {
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity)
                    {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else if (getActivity() instanceof WeatherActivity)
                    {
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }
                }
            }
        });

        chooseareaMytitleId.setLeftListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (currentLevel == LEVEL_COUNTY)
                {
                    queryCitys();
                } else if (currentLevel == LEVEL_CITY)
                {
                    queryProvinces();
                }
            }
        });
        queryProvinces();
    }

    public static ChooseAreaFragment newInstance()
    {
        return new ChooseAreaFragment();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryProvinces()
    {
        chooseareaMytitleId.setTitle("中国");
        chooseareaMytitleId.setLeftVisibility(1);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0)
        {
            dataList.clear();
            for (Province province : provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            chooseareaListviewId.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, 0);
        }
    }

    /**
     * 查询全国所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCitys()
    {
        chooseareaMytitleId.setTitle(selectedProvince.getProvinceName());
        chooseareaMytitleId.setLeftVisibility(0);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0)
        {
            dataList.clear();
            for (City city : cityList)
            {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            chooseareaListviewId.setSelection(0);
            currentLevel = LEVEL_CITY;
        }
        else
        {
            int provinceId = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceId;
            queryFromServer(address, 1);
        }
    }

    /**
     * 查询全国所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCountys()
    {
        chooseareaMytitleId.setTitle(selectedCity.getCityName());
        chooseareaMytitleId.setLeftVisibility(0);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0)
        {
            dataList.clear();
            for (County county : countyList)
            {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            chooseareaListviewId.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        }
        else
        {
            int provinceId = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceId + "/" + cityCode;
            queryFromServer(address, 2);
        }
    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */
    private void queryFromServer(String address, final int type)
    {
        showProgress("天气君正在加载之中");
        OKHttpUtils.sendOkHttpRequest(address, new Callback()
        {
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String responseText = response.body().string();
                boolean result = false;
                if (type == 0)
                {
                    result = JsonUtil.handleProviceData(responseText);
                }
                else if (type == 1)
                {
                    result = JsonUtil.handleCityData(responseText, selectedProvince.getId());
                }
                else if (type == 2)
                {
                    result = JsonUtil.handleCountyData(responseText, selectedCity.getId());
                }
                if (result)
                {
                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            hideProgress();
                            if (type == 0)
                            {
                                queryProvinces();
                            } else if (type == 1)
                            {
                                queryCitys();
                            } else if (type == 2)
                            {
                                queryCountys();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e)
            {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        hideProgress();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
