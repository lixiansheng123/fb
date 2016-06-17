package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/8
 */
public class HotSpecialListBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<IdNameLogoBean> list;

        public List<IdNameLogoBean> getList() {
            return list;
        }

        public void setList(List<IdNameLogoBean> list) {
            this.list = list;
        }
    }
}
