package com.aji.newsweather.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @author misszhang
 */
public abstract class BaseFragment extends Fragment implements IBaseView
{
    private View mLayoutView;

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mLayoutView != null)
        {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null)
            {
                parent.removeView(mLayoutView);
            }
        }
        else
        {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            initView();     //初始化布局
        }

        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container)
    {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus()
    {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public BaseActivity getBaseActivity()
    {
        return (BaseActivity) getActivity();
    }

    @Override
    public void showProgress(String message)
    {
        if (getStatus())
        {
            getBaseActivity().showProgress(message);
        }
    }

    @Override
    public void hideProgress()
    {
        if (getStatus())
        {
            getBaseActivity().hideProgress();
        }
    }

    @Override
    public void showToast(String msg)
    {
        if (getStatus())
        {
            getBaseActivity().showToast(msg);
        }
    }
}
