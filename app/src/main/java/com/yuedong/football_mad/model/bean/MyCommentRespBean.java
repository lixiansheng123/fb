package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/12
 */
public class MyCommentRespBean extends ListResponse<MyCommentBean> {
    private Data data;

    @Override
    public List<MyCommentBean> getDataList() {
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
        private List<MyCommentBean> list;

        public List<MyCommentBean> getList() {
            return list;
        }

        public void setList(List<MyCommentBean> list) {
            this.list = list;
        }
    }
}
