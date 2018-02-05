package com.aji.newsweather.ui;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.aji.newsweather.R;
import com.aji.newsweather.application.MyApplication;
import com.aji.newsweather.base.BaseActivity;
import com.aji.newsweather.ui.fragment.ChooseAreaFragment;
import com.aji.newsweather.util.ActivityUtil;
import com.aji.newsweather.util.ToastUtils;
import com.aji.newsweather.util.UiUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity
{
    private long exitTime = 0;
    @BindView(R.id.satuts_id)
    LinearLayout satuts_id;
    @Override
    protected int getContentResId()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

//        if (Build.VERSION.SDK_INT >= 21)
//        {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        return R.layout.activity_main;
    }


    @Override
    public void initView()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        {
            satuts_id.setVisibility(View.GONE);
        }
        ChooseAreaFragment chooseAreaFragment = (ChooseAreaFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (chooseAreaFragment == null)
        {
            chooseAreaFragment = ChooseAreaFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager() , chooseAreaFragment , R.id.contentFrame);
        }
    }

    @Override
    public void initData()
    {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (System.currentTimeMillis() - exitTime > 2000)
            {
                ToastUtils.showString(this, UiUtils.getStringFromID(R.string.exitProcedureAgain));
                exitTime = System.currentTimeMillis();
            } else {
                closeApplication();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出APP
     */
    private void closeApplication()
    {
        MyApplication application = (MyApplication) getApplication();
        application.closeApplication();
    }

}
