package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuListRespBean extends ListResponse<FinalCompetitionBean> {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public List<FinalCompetitionBean> getDataList() {
        return getData().getList();
    }

    @Override
    public Data getData() {
        return data;
    }

    public class Data extends ListResponse.Data{
        private List<FinalCompetitionBean> list;

        public List<FinalCompetitionBean> getList() {
            return list;
        }

        public void setList(List<FinalCompetitionBean> list) {
            this.list = list;
        }
    }

}
