package com.example.alen.commonproject.Manager;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by Alen on 2017/10/10.
 * 自定义Glide Module
 * 1. 设置glide 加载策略
 * 2. 设置缓存目录
 * 3. 设置图片加载质量
 */


/**
 * 使用Glide 加载图片最好做如下设置
 * 1. placeholder 在加载开始 -- 加载完成，这段时间显示的图片
 * 2. error 在加载失败的情况下，显示的图片
 * 3. 注意 with 的生命周期控制
 * 4. 选择性的设置  priority
 * 5. 加载动画 crossFade   crossFade(int time)
 * 6. 选择性的 先加载缩略图   thumbnail(0.1f)
 * 7. 选择 centerCorp 或者 centerFit
 * Glide.with(this).load("").placeholder(R.mipmap.image_loading_default).error(R.mipmap.imgfail)
 * .dontAnimate().centerCrop().priority(Priority.NORMAL).thumbnail(0.1f).into(view);
 */

public class GlideManager implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //定义缓存大小为100M
        int diskCacheSize = 100 * 1024 * 1024;

        //自定义缓存 路径 和 缓存大小
        String diskCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/milin/cache/";

        //提高图片质量
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);

        //自定义磁盘缓存:这种缓存只有自己的app才能访问到
        // builder.setDiskCache( new InternalCacheDiskCacheFactory( context , diskCacheSize )) ;
        // builder.setDiskCache( new InternalCacheDiskCacheFactory( context , diskCachePath , diskCacheSize )) ;

        //自定义磁盘缓存：这种缓存存在SD卡上，所有的应用都可以访问到
        builder.setDiskCache(new DiskLruCacheFactory(diskCachePath, diskCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}


