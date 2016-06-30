package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/30
 */
public class NewsDetailRespBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private NewsDetailBean list;

        public NewsDetailBean getList() {
            return list;
        }

        public void setList(NewsDetailBean list) {
            this.list = list;
        }
    }
}
