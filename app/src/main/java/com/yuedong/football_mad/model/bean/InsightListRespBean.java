package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/21
 */
public class InsightListRespBean extends ListResponse<InsightBean> {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public List<InsightBean> getDataList() {
        return getData().getList();
    }

    @Override
    public Data getData() {
        return data;
    }

    public class Data extends ListResponse.Data{
        private List<InsightBean> list;

        public List<InsightBean> getList() {
            return list;
        }

        public void setList(List<InsightBean> list) {
            this.list = list;
        }
    }
}
