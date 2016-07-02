package com.yuedong.lib_develop.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.WindowUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class App extends Application {
    private static App instance;
    private static Handler handler;
    private Context context;
    private Integer[] phoneWh;
    private static RequestQueue mRequstQueue;

    public static RequestQueue getRequestQueue() {
        return mRequstQueue;
    }

    // 线程池
    private Executor executor;

    public static App getInstance() {
        return instance;
    }

    public Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        instance = this;
        context = getApplicationContext();
        phoneWh = WindowUtils.getPhoneWH(this);
        L.i("PHONE-PARAMS", "手机屏幕参数width" + phoneWh[0] + "::height:" + phoneWh[1]);
        executor = Executors.newCachedThreadPool();
        handler = new Handler();
        mRequstQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
