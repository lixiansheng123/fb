package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/11
 */
public class CollectCommentRespBean extends ListResponse<CollectCommentBean> {
    private Data data;

    @Override
    public List<CollectCommentBean> getDataList() {
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
        private List<CollectCommentBean> list;

        public List<CollectCommentBean> getList() {
            return list;
        }

        public void setList(List<CollectCommentBean> list) {
            this.list = list;
        }
    }
}
