package com.yuedong.lib_develop.bean;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/3.
 */
public class RequestParamsMap extends BaseRequest {
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
