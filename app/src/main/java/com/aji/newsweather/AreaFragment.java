package com.aji.newsweather;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.aji.newsweather.base.BaseFragment;
import com.aji.newsweather.db.Province;
import com.aji.newsweather.net.HttpUtil;
import com.aji.newsweather.net.IpService;
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
        return R.layout.choose_area;
    }

    @Override
    public void initView()
    {
        fragmentMytitleId.setLeftVisibility(1);
        fragmentMytitleId.setRightVisibility(1);
        httpUtil = HttpUtil.getInstance();
        queryProvinces();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        fragmentListviewId.setAdapter(adapter);
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
            for (Province province : provinces) {
                dataList.add(province.getName());
            }
            fragmentListviewId.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        }
        else
        {
            getFromServerProvince();
        }
    }
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        httpUtil.unsubscribe();
    }
}
