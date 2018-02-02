package com.aji.newsweather.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aji.newsweather.application.MyApplication;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 *
 * @author misszhang
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView
{
    private Toast toast;
    private ProgressDialog mProgressDialog;
    private Unbinder unbinder;
    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化数据中心
     */
    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getContentResId());
        // 初始化View注入
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.addActivity(this);
    }

    protected abstract int getContentResId();

    @Override
    public void finish()
    {
        super.finish();
    }

    @Override
    public void showProgress(String message)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgress()
    {
        if (mProgressDialog == null)
        {
            return;
        }

        if (mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg)
    {
        if (!isFinishing())
        {
            if (toast == null)
            {
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else
            {
                toast.setText(msg);
            }

            toast.show();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
        MyApplication myApplication = (MyApplication) getApplication();
        myApplication.removeActivity(this);
    }
}
