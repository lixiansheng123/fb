package com.yuedong.lib_develop.net;

import android.os.Handler;
import android.text.TextUtils;

import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.bean.BaseRequest;
import com.yuedong.lib_develop.bean.RequestParamsJson;
import com.yuedong.lib_develop.bean.RequestParamsMap;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.SignUtils;
import java.util.LinkedList;

public class VolleyConnectUtils {
    private static VolleyConnectUtils connectUtils;
    private Handler handler = new Handler();
    private LinkedList<String> mTasks = new LinkedList<String>();
    private NCache nCache;

    public VolleyConnectUtils() {
        nCache = new NFileCache();
    }

    public static VolleyConnectUtils getInstance() {
        synchronized (VolleyConnectUtils.class) {
            if (connectUtils == null) {
                connectUtils = new VolleyConnectUtils();
            }
        }
        return connectUtils;
    }

    /**
     * 设置缓存
     *
     * @param nCache
     */
    public void setNCache(NCache nCache) {
        if (nCache != null)
            this.nCache = nCache;
    }

    public String doGet(RequestParamsMap baseRequest) {
        return goToNetWork(baseRequest, 0);
    }

    public String doPost(RequestParamsMap baseRequest) {
        return goToNetWork(baseRequest, 1);
    }

    public String doPostByJson(RequestParamsJson baseRequest) {
        return goToNetWork(baseRequest, 2);
    }

    private String goToNetWork(final BaseRequest baseRequest, final int type) {
        if(baseRequest == null)return null;
        String task = "";
            if(TextUtils.isEmpty(baseRequest.getTag())){
                task = SignUtils.md5(baseRequest.getUrl());
                baseRequest.setTag(task);
            }else{
                task = baseRequest.getTag();
            }
        mTasks.add(task);
        VolleyRequst.setNCacheWay(nCache);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (type == 0)
                    VolleyRequst.doGet(baseRequest);
                else if (type == 1)
                    VolleyRequst.doPost((RequestParamsMap) baseRequest);
                else
                    VolleyRequst.doPostByJson((RequestParamsJson) baseRequest);
            }
        }, 300);
        return task;
    }


    /**
     * 取消任务
     */
    public void cancleTask() {
        if (mTasks != null && !mTasks.isEmpty()) {
            L.d("取消任务成功");
            for (String str : mTasks) {
                App.getRequestQueue().cancelAll(str);
            }
            mTasks.clear();
        }
    }

}
