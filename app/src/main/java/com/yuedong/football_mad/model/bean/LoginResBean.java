package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/15
 */
public class LoginResBean extends BaseResponse {
    public User data;
    public User getData() {
        return data;
    }
    public void setData(User data) {
        this.data = data;
    }
}
