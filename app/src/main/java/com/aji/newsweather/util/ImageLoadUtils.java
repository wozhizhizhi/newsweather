package com.aji.newsweather.util;

import android.content.Context;
import android.widget.ImageView;

import com.aji.newsweather.R;
import com.bumptech.glide.Glide;

/**
 * 图片加载工具类
 * 为方便以后随时更换图片加载库
 * 并且统一配置图片加载方式
 *
 * @author misszhang
 */
public class ImageLoadUtils
{

    public static final int DEFAULT_PLACEHOLDER_RESID = R.mipmap.default_image;

    public static void loadImage(Context context, String imageUrl, int resId, ImageView imageView)
    {
        Glide
                .with(context)
                .load(imageUrl)
                .placeholder(resId)
                .into(imageView);
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView)
    {
        loadImage(context, imageUrl, DEFAULT_PLACEHOLDER_RESID, imageView);
    }
}
