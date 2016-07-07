package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/7/7
 */
public class HeadRespBean extends BaseResponse {
    public HeadBean data;

    public HeadBean getData() {
        return data;
    }

    public void setData(HeadBean data) {
        this.data = data;
    }
}
