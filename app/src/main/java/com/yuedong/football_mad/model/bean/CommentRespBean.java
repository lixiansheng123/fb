package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/27
 */
public class CommentRespBean extends ListResponse<List<CommentBean>> {
    private Data data;

    @Override
    public List<List<CommentBean>> getDataList() {
        return getData().getList();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List<List<CommentBean>> list;

        public List<List<CommentBean>> getList() {
            return list;
        }

        public void setList(List<List<CommentBean>> list) {
            this.list = list;
        }
    }
}
