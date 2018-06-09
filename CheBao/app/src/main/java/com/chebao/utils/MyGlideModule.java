package com.chebao.utils;

/**
 * 创建日期：2018/6/4 on 15:11
 * 描述:
 * 作者:jackson Administrator
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * 自定义模块
 * <p>
 * 目前Glide还无法识别我们自定义的MyGlideModule，
 * 如果想要让它生效，
 * 还得在AndroidManifest.xml文件当中加入如下配置才行
 */
public class MyGlideModule implements GlideModule {
    /**
     * setMemoryCache()
     * 用于配置Glide的内存缓存策略，默认配置是LruResourceCache。
     * <p>
     * setBitmapPool()
     * 用于配置Glide的Bitmap缓存池，默认配置是LruBitmapPool。
     * <p>
     * setDiskCache()
     * 用于配置Glide的硬盘缓存策略，默认配置是InternalCacheDiskCacheFactory。
     * <p>
     * setDiskCacheService()
     * 用于配置Glide读取缓存中图片的异步执行器，默认配置是FifoPriorityThreadPoolExecutor，
     * 也就是先入先出原则。
     * <p>
     * setResizeService()
     * 用于配置Glide读取非缓存中图片的异步执行器，默认配置也是FifoPriorityThreadPoolExecutor。
     * <p>
     * setDecodeFormat()
     * 用于配置Glide加载图片的解码模式，默认配置是RGB_565。
     */

    public static final int DISK_CACHE_SIZE = 500 * 1024 * 1024;
    public static final String DISK_CACHE_NAME = "chebao";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        /**
         将所有Glide加载的图片缓存到SD卡上,
         默认硬盘缓存大小都是250M,这里改为500
         * */
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));

        /**
         ExternalCacheDiskCacheFactory的默认缓存路径
         是在sdcard/Android/data/包名/cache/image_manager_disk_cache目录当中
         */
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE));

        /**
         * 更改缓存最总文件夹名称
         *
         * 是在sdcard/Android/data/包名/cache/DISK_CACHE_NAME目录当中
         */
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, DISK_CACHE_NAME, DISK_CACHE_SIZE));

        /**
         * Glide也能使用ARGB_8888的图片格式
         * 虽然图片质量变好了，但同时内存开销也会明显增大
         */
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}