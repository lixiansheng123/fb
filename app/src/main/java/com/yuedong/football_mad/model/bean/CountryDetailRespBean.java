package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/23
 */
public class CountryDetailRespBean extends BaseResponse{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private CountryDetailBean list;

        public CountryDetailBean getList() {
            return list;
        }

        public void setList(CountryDetailBean list) {
            this.list = list;
        }
    }
}
