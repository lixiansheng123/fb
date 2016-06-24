package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/24
 */
public class OtherDetailRespBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private OtherDetailBean list;

        public OtherDetailBean getList() {
            return list;
        }

        public void setList(OtherDetailBean list) {
            this.list = list;
        }
    }
}
