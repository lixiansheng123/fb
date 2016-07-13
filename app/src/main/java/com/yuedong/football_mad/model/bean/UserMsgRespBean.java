package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class UserMsgRespBean extends ListResponse<UserMsgBean> {
    private Data data;

    @Override
    public List<UserMsgBean> getDataList() {
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
        private List<UserMsgBean> list;

        public List<UserMsgBean> getList() {
            return list;
        }

        public void setList(List<UserMsgBean> list) {
            this.list = list;
        }
    }
}
