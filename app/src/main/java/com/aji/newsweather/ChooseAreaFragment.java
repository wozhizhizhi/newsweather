package com.aji.newsweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.aji.newsweather.base.BaseFragment;
import com.aji.newsweather.db.City;
import com.aji.newsweather.db.County;
import com.aji.newsweather.db.Province;
import com.aji.newsweather.net.OKHttpUtils;
import com.aji.newsweather.net.ResultCallback;
import com.aji.newsweather.util.JsonUtil;
import com.aji.newsweather.weight.MyTitle;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;
import butterknife.BindView;
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

    private ArrayAdapter<String> adapter;

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

    private List<String> dataList;

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

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();
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

    private void queryFromServer(String address , final int type)
    {
        showProgress("天气君正在加载之中");
        OKHttpUtils.getInstance(getActivity()).getAsynHttp(address, new ResultCallback()
        {
            @Override
            public void onError(Request request, Exception e)
            {
                hideProgress();
                Toast.makeText(getActivity() , "天气君施法失败，请重试!" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                String responseStr = response.body().toString();
                boolean result = false;
                switch (type)
                {
                    case 0:
                        try
                        {
                            result = JsonUtil.handleProviceData(responseStr);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        break;
                    case 1:

                        break;

                    case 2:
                        break;
                }
                if(result == true)
                {
                    if (type == 0)
                    {
                        queryProvinces();
                    }
                }

            }
        });
    }

}
