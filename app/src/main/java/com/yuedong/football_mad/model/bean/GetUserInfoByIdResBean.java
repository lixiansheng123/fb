package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/15
 */
public class GetUserInfoByIdResBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private User list;

        public User getList() {
            return list;
        }

        public void setList(User list) {
            this.list = list;
        }
    }
}
