package com.yuedong.lib_develop.bean;

import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

public class BaseRequest {
    private String url;
    private VolleyNetWorkCallback callback;
    private String tag;
    private Class<? extends BaseResponse> responseObj;
    private boolean cacheData = false;

    public Class<? extends BaseResponse> getResponseObj() {
        return responseObj;
    }

    public void setResponseObj(Class<? extends BaseResponse> responseObj) {
        this.responseObj = responseObj;
    }

    public boolean isCacheData() {
        return cacheData;
    }

    public void setCacheData(boolean cacheData) {
        this.cacheData = cacheData;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public VolleyNetWorkCallback getCallback() {
        return callback;
    }

    public void setCallback(VolleyNetWorkCallback callback) {
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
