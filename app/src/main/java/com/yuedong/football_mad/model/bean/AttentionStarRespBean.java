package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class AttentionStarRespBean extends ListResponse<AttentionStarBean> {
    private Data data;

    @Override
    public List<AttentionStarBean> getDataList() {
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
        private List<AttentionStarBean> list;

        public List<AttentionStarBean> getList() {
            return list;
        }

        public void setList(List<AttentionStarBean> list) {
            this.list = list;
        }
    }
}
