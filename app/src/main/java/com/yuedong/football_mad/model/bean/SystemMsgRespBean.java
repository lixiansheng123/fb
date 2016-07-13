package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class SystemMsgRespBean extends ListResponse<SystemMsgBean>{
    private Data data;

    @Override
    public List<SystemMsgBean> getDataList() {
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
        private List<SystemMsgBean> list;

        public List<SystemMsgBean> getList() {
            return list;
        }

        public void setList(List<SystemMsgBean> list) {
            this.list = list;
        }
    }

}
