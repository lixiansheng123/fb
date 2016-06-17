package com.yuedong.lib_develop.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.yuedong.lib_develop.app.App;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonStringRequest extends JsonRequest<NetworkResponse> {

    public JsonStringRequest(int method, String url, JSONObject jsonRequest, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest == null ? null : jsonRequest.toString(), listener, errorListener);
    }

    public JsonStringRequest(String url, JSONObject jsonRequest, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        this(jsonRequest == null ? 0 : 1, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Charset", "UTF-8");
//        headers.put("Content-Type", "text/html");
//        headers.put("Accept-Encoding", "gzip, deflate");
        String sessionId = (String) SPUtils.get(App.getInstance().getAppContext(), "key_session_id", "");
        if (StringUtil.isNotEmpty(sessionId))
            headers.put("PHPSESSID", sessionId);
        L.d("请求头" + headers);
        return headers;
    }
}
