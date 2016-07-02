package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/1
 */
public class GameListRespBean extends ListResponse<GameListBean> {
    private Data data;

    @Override
    public List<GameListBean> getDataList() {
        return getData().getList();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List<GameListBean> list;

        public List<GameListBean> getList() {
            return list;
        }

        public void setList(List<GameListBean> list) {
            this.list = list;
        }
    }
}
