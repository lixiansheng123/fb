package com.yuedong.lib_develop.net;

import com.android.volley.VolleyError;
import com.yuedong.lib_develop.bean.BaseResponse;

public interface VolleyNetWorkCallback {

    void onNetworkStart(String tag);

    void onNetworkSucceed(String tag, BaseResponse data);

    void onNetworkError(String tag, VolleyError error);

}
