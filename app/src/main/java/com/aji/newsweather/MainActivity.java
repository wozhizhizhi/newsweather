package com.aji.newsweather;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.aji.newsweather.base.BaseActivity;
import com.aji.newsweather.util.ActivityUtil;

public class MainActivity extends BaseActivity
{

    @Override
    protected int getContentResId()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return R.layout.activity_main;
    }


    @Override
    public void initView()
    {
        ChooseAreaFragment chooseAreaFragment = (ChooseAreaFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (chooseAreaFragment != null)
        {
//           areaFragment = AreaFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager() , chooseAreaFragment , R.id.contentFrame);
        }
    }

    @Override
    public void initData()
    {

    }
}
