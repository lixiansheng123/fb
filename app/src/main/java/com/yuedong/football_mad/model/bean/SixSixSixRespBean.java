package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class SixSixSixRespBean extends ListResponse<SixSixSixBean>{
    private Data data;

    @Override
    public List<SixSixSixBean> getDataList() {
        return getData().getList();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List<SixSixSixBean> list;

        public List<SixSixSixBean> getList() {
            return list;
        }

        public void setList(List<SixSixSixBean> list) {
            this.list = list;
        }
    }
}
