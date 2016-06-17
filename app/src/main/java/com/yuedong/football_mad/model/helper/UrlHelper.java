package com.yuedong.football_mad.model.helper;

import com.yuedong.football_mad.app.Constant;
import com.yuedong.lib_develop.utils.StringUtil;

/**
 * @author 俊鹏 on 2016/6/8
 */
public class UrlHelper {
    /**
     * 检查url
     *
     * @param url
     * @return
     */
    public static String checkUrl(String url) {
        if (StringUtil.isEmpty(url)) return "";
        String checkUrl = url;
        if (url.indexOf("http://") != -1) {
        } else {
            checkUrl = Constant.URL_PIC + url;
        }
        return checkUrl;
    }
}
