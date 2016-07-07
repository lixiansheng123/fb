package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/7/6
 */
public class SpecialDetailRespBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private SpecialDetailBean item;

        public SpecialDetailBean getItem() {
            return item;
        }

        public void setItem(SpecialDetailBean item) {
            this.item = item;
        }
    }
}
