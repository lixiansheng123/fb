package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/21
 */
public class TouchListRespBean extends ListResponse<TouchBean> {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public List<TouchBean> getDataList() {
        return getData().getList();
    }

    public class Data extends ListResponse.Data{
        private List<TouchBean> list;

        public List<TouchBean> getList() {
            return list;
        }

        public void setList(List<TouchBean> list) {
            this.list = list;
        }
    }
}
