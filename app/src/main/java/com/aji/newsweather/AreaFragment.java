package com.aji.newsweather;

import android.widget.ListView;

import com.aji.newsweather.base.BaseFragment;
import com.aji.newsweather.weight.MyTitle;

import butterknife.BindView;

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
        fragmentMytitleId.setLeftVisibility(0);
        fragmentMytitleId.setRightVisibility(1);
    }

}
