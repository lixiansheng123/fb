package com.yuedong.lib_develop.bean;

import org.json.JSONObject;

public class RequestParamsJson extends BaseRequest {
    public JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
