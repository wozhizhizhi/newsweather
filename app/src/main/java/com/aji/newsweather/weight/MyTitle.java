package com.aji.newsweather.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aji.newsweather.R;

/**
 * Author:zhangmiss on 2018/01/29 0029.
 * mail:867596152@qq.com
 * Descripiton:
 */

public class MyTitle extends RelativeLayout
{
    private ImageView my_title_left_iv;
    private TextView my_title_text;
    private ImageView my_title_right_iv;
    private RelativeLayout relativeLayout_title_id;
    private String titleName;
    private int bgColor;
    private int title_right_iv;
    private int titlt_left_iv;


    public MyTitle(Context context)
    {
        super(context);
        initView(context);
    }

    public MyTitle(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initTyped(context, attrs);
        initView(context);
    }

    public MyTitle(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initTyped(context, attrs);
        initView(context);
    }


    private void initTyped(Context context , AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs , R.styleable.MyTitle);
        bgColor =  typedArray.getColor(R.styleable.MyTitle_my_title_bg , getResources().getColor(R.color.colorAccent));
        titleName = typedArray.getString(R.styleable.MyTitle_title_name);
        // 设置Imageview的图片
        titlt_left_iv = typedArray.getResourceId(R.styleable.MyTitle_my_title_leftiv , R.mipmap.ico_return);
        title_right_iv = typedArray.getResourceId(R.styleable.MyTitle_my_title_rightiv , R.mipmap.title_right);
    }

    private void initView(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.app_title , this , true);
        my_title_left_iv = findViewById(R.id.my_title_left_iv);
        my_title_right_iv = findViewById(R.id.my_title_right_iv);
        my_title_text = findViewById(R.id.my_title_text);
        relativeLayout_title_id = findViewById(R.id.relativeLayout_title_id);

        relativeLayout_title_id.setBackgroundColor(bgColor);
        my_title_text.setText(titleName);
        my_title_left_iv.setImageResource(titlt_left_iv);
        my_title_right_iv.setImageResource(title_right_iv);
    }

    public void setLeftListener(OnClickListener onClickListener)
    {
        my_title_left_iv.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener)
    {
        my_title_right_iv.setOnClickListener(onClickListener);
    }

    public void setRightVisibility(int visibility)
    {
        if(visibility == 0)
        {
            my_title_right_iv.setVisibility(View.VISIBLE);
        }
        else
        {
            my_title_right_iv.setVisibility(View.GONE);
        }
    }

    public void setLeftVisibility(int visibility)
    {
        if(visibility == 0)
        {
            my_title_left_iv.setVisibility(View.VISIBLE);
        }
        else
        {
            my_title_left_iv.setVisibility(View.GONE);
        }
    }
}
