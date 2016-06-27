package com.yuedong.lib_develop.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.bean.BaseRequest;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.RequestParamsJson;
import com.yuedong.lib_develop.bean.RequestParamsMap;
import com.yuedong.lib_develop.utils.JsonStringRequest;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.NetUtils;
import com.yuedong.lib_develop.utils.SPUtils;
import com.yuedong.lib_develop.utils.SignUtils;

import org.json.JSONObject;

public class VolleyRequst {
    private static StringRequest mStirngRequest;
    private static JsonRequest<NetworkResponse> mJsonRequest;
    private static RequestQueue mRequestQueue = App.getRequestQueue();
    private static Gson mGson = new GsonBuilder().create();
    public static int OK = 2000000;
    public static int TIME_OUT = 10000;
    public static NCache mNCache = new NFileCache();
    private static String SAVE_SESSION_FLAG = "http://www.zqfeng.com/crazyfootball/user/userlogin";

    /**
     * 设置缓存
     *
     * @param nCache
     */
    public static void setNCacheWay(NCache nCache) {
        if (nCache != null)
            mNCache = nCache;
    }

    public static void doGet(final BaseRequest request) {
        L.d("请求的URL" + request.getUrl());
        request.getCallback().onNetworkStart(request.getTag());
        if (!NetUtils.isConnected(App.getInstance().getAppContext())) {
            request.getCallback().onNetworkSucceed(request.getTag(), toObj(mNCache.getData(request.getTag()), request.getResponseObj()));
            VolleyError error = new VolleyError("网络异常,请检查网络");
            request.getCallback().onNetworkError(request.getTag(), error);
            return;
        }
        mRequestQueue.cancelAll(request.getTag());
        mStirngRequest = new StringRequest(Method.GET, request.getUrl(), new Response.Listener<String>() {
            public void onResponse(String data) {
                L.d(request.getUrl() + "成功返回JSON" + data);
                BaseResponse baseResponse = toObj(data, request.getResponseObj());
                if (baseResponse != null && baseResponse.getState().getCode() == OK && request.isCacheData())
                    mNCache.cacheData(request.getTag(), data);
                request.getCallback().onNetworkSucceed(request.getTag(), baseResponse);
            }

            ;
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                request.getCallback().onNetworkError(request.getTag(), error);
            }
        });
        startRequest(mStirngRequest);
    }

    public static void doPost(final RequestParamsMap request) {
        L.d("请求的URL==" + request.getUrl());
        Map<String, String> params = request.getParams();
        if (params != null && !params.isEmpty())
            L.d("请求的参数==" + params.toString());
        request.getCallback().onNetworkStart(request.getTag());
        if (!NetUtils.isConnected(App.getInstance().getAppContext())) {
            request.getCallback().onNetworkSucceed(request.getTag(), toObj(mNCache.getData(request.getTag()), request.getResponseObj()));
            VolleyError error = new VolleyError("网络异常,请检查网络");
            request.getCallback().onNetworkError(request.getTag(), error);
            return;
        }
        mRequestQueue.cancelAll(request.getTag());
        mStirngRequest = new StringRequest(Method.POST, request.getUrl(), new Response.Listener<String>() {
            public void onResponse(String data) {
                L.d(request.getUrl() + "成功返回JSON" + data);
                BaseResponse baseResponse = toObj(data, request.getResponseObj());
                if (baseResponse != null && baseResponse.getState().getCode() == OK && request.isCacheData())
                    mNCache.cacheData(request.getTag(), data);
                request.getCallback().onNetworkSucceed(request.getTag(), baseResponse);
            }

            ;
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                request.getCallback().onNetworkError(request.getTag(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return request.getParams() != null ? request.getParams() : super.getParams();
            }
        };
        startRequest(mStirngRequest);
    }

    /**
     * post data为json
     *
     * @param request
     */
    public static void doPostByJson(final RequestParamsJson request) {
        L.d("请求的URL==" + request.getUrl() + "===tag为=========>>" + request.getTag());
        JSONObject params = request.getJsonObject();
        if (params != null)
            L.d("请求的参数==" + params.toString());
        request.getCallback().onNetworkStart(request.getTag());
        if (!NetUtils.isConnected(App.getInstance().getAppContext())) {
            BaseResponse baseResponse = null;
            if (request.isUseCache()){
                String json = mNCache.getData(request.getTag());
                baseResponse = toObj(json,request.getResponseObj());
                if(baseResponse != null)baseResponse.setJson(json);
            }
            request.getCallback().onNetworkSucceed(request.getTag(), baseResponse);
            VolleyError error = new VolleyError("网络异常,请检查网络");
            request.getCallback().onNetworkError(request.getTag(), error);
            return;
        }
        mRequestQueue.cancelAll(request.getTag());
        mJsonRequest = new JsonStringRequest(Method.POST, request.getUrl(), request.getJsonObject(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String data;
                try {
                    data = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException var4) {
                    data = new String(response.data);
                }
                L.d("响应json========>>");
                L.d(data);
                BaseResponse baseResponse = toObj(data, request.getResponseObj());
                if(baseResponse != null)baseResponse.setJson(data);
                if (baseResponse != null && baseResponse.getState().getCode() == OK && request.isCacheData())
                    mNCache.cacheData(request.getTag(), data);
                request.getCallback().onNetworkSucceed(request.getTag(), baseResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                L.d(request.getUrl() + "===请求失败===" + volleyError.getMessage());
                request.getCallback().onNetworkError(request.getTag(), volleyError);
            }
        });
        startRequest(mJsonRequest);
    }


    private static void startRequest(Request request) {
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        TIME_OUT,//默认超时时间
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        mRequestQueue.add(request);
//        mRequestQueue.start();
    }

    private static BaseResponse toObj(String json, Class<? extends BaseResponse> cls) {
        if (cls != null) {
            try {
                return mGson.fromJson(json, cls);
            } catch (Exception e) {
                e.printStackTrace();
                L.d("JSON解析失败");
            }
        }
        return null;
    }

}
