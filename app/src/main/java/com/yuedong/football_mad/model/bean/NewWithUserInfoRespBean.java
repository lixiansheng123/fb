package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/14
 */
public class NewWithUserInfoRespBean extends ListResponse<NewWithUserInfoBean>{
    private Data data;

    @Override
    public List<NewWithUserInfoBean> getDataList() {
        return getData().getList();
    }

    @Override
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List<NewWithUserInfoBean> list;

        public List<NewWithUserInfoBean> getList() {
            return list;
        }

        public void setList(List<NewWithUserInfoBean> list) {
            this.list = list;
        }
    }

}
