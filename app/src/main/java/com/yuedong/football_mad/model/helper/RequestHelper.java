package com.yuedong.football_mad.model.helper;


import com.yuedong.football_mad.app.Constant;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.RequestParamsJson;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.SignUtils;
import com.yuedong.lib_develop.net.VolleyConnectUtils;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RequestHelper {
    /**
     * POST
     *
     * @param url
     * @param data
     * @param cls
     * @param cache
     * @param callback
     * @return
     */
    public static String post(String url, Map<String, String> data, Class<? extends BaseResponse> cls, boolean cache, boolean useCache, VolleyNetWorkCallback callback) {
        String task = null;
        try {
            RequestParamsJson baseRequest = new RequestParamsJson();
            baseRequest.setUrl(url);
            baseRequest.setResponseObj(cls);
            baseRequest.setCacheData(cache);
            baseRequest.setUseCache(useCache);
            baseRequest.setCallback(callback);
            JSONObject allObj = new JSONObject();
            JSONObject clientObj = new JSONObject();
            clientObj.put("caller", Constant.CALLER);
            allObj.put("rid", Math.round(System.currentTimeMillis() / 1000));
//            allObj.put("rid", System.currentTimeMillis());
            allObj.put("client", clientObj);
//            allObj.put("encrypt", "md5");
//            allObj.put("format", "json");
//            allObj.put("v", "1.0");
            String sign = null;
            JSONObject dataObj = new JSONObject();
            if (data != null) {
                Set<String> keysSet = data.keySet();
                String[] keys = new String[keysSet.size()];
                Iterator<String> it = keysSet.iterator();
                int i = 0;
                while (it.hasNext()) {
                    keys[i] = it.next();
                    i++;
                }
                Arrays.sort(keys);
                StringBuilder sb = new StringBuilder();
                sb.append(Constant.CALLER);
                for (String key : keys) {
                    String value = data.get(key);
                    dataObj.put(key, value);
                    sb.append(key).append("=").append(value);
                }
                sb.append(Constant.S_KEY);
                sign = SignUtils.md5(sb.toString());
            }
            if (sign == null)
                sign = SignUtils.md5(Constant.CALLER + Constant.S_KEY);
            allObj.put("data", dataObj);
            allObj.put("sign", sign);
            baseRequest.setJsonObject(allObj);
            L.d("请求的json数据" + allObj.toString());
            task = VolleyConnectUtils.getInstance().doPostByJson(baseRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * 取消所有任务
     */
    public static void cancleAll() {
        VolleyConnectUtils.getInstance().cancleTask();
    }
}
