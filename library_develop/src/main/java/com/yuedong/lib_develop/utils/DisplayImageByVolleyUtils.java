package com.yuedong.lib_develop.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.bean.SimplePicConfig;
import com.yuedong.library_develop.R;

/**
 * 图片下载工具类
 */
public class DisplayImageByVolleyUtils {
    private static SimplePicConfig config = new SimplePicConfig();
    private static ImageLoader imageloader = new ImageLoader(Volley.newRequestQueue(App.getInstance().getAppContext()), new BitmapCache());


    public static final ImageLoader IMAGELOADER = new ImageLoader(Volley.newRequestQueue(App.getInstance().getAppContext()),
            new BitmapCache());

    public static void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, config);
    }

    public static void loadImage(String url, ImageView imageView, SimplePicConfig config) {
        if (url == null)
            url = "";
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, config.getLoadPic(), config.getErrorPic());
        imageloader.get(url, imageListener);
    }

    public static void loadImage(NetworkImageView niv, String url) {
        loadImage(niv, url, config);
    }


    public static void loadImage(NetworkImageView niv, String url, SimplePicConfig config) {
        if (url == null)
            url = "";
        niv.setErrorImageResId(config.getErrorPic());
        niv.setDefaultImageResId(config.getLoadPic());
        niv.setImageUrl(url, IMAGELOADER);
    }

    public static void loadBallDefault(String url, ImageView imageView) {
        SimplePicConfig simplePicConfig = new SimplePicConfig();
        simplePicConfig.setErrorPic(R.drawable.ic_default3);
        simplePicConfig.setLoadPic(R.drawable.ic_default3);
        if(imageView instanceof NetworkImageView)
            loadImage((NetworkImageView)imageView,url,simplePicConfig);
        else
            loadImage(url, imageView, simplePicConfig);
    }

    public static void loadUserPic(String url,ImageView imageView){
        SimplePicConfig simplePicConfig = new SimplePicConfig();
        simplePicConfig.setErrorPic(R.drawable.ic_default_head);
        simplePicConfig.setLoadPic(R.drawable.ic_default_head);
        if(imageView instanceof NetworkImageView)
            loadImage((NetworkImageView)imageView,url,simplePicConfig);
        else
            loadImage(url, imageView, simplePicConfig);
    }

    public static void loadQuadratePic(String url,ImageView imageView){
        SimplePicConfig simplePicConfig = new SimplePicConfig();
        simplePicConfig.setErrorPic(R.drawable.ic_default2);
        simplePicConfig.setLoadPic(R.drawable.ic_default2);
        if(imageView instanceof NetworkImageView)
            loadImage((NetworkImageView)imageView,url,simplePicConfig);
        else
            loadImage(url, imageView, simplePicConfig);
    }


    static final class BitmapCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            // 获取程序运行最大内存
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            // 初始化图片内存缓存
            mCache = new LruCache<String, Bitmap>(maxMemory / 8) {
                // 重写sizeof方法 让其知道图片的大小
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    if (value == null)
                        return 0;
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            if (bitmap != null) {
                mCache.put(url, bitmap);
            }
        }
    }

}
