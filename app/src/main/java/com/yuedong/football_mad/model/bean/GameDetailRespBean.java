package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/7/4
 */
public class GameDetailRespBean extends BaseResponse {
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data{
        private GameDetailBean list;

        public GameDetailBean getList() {
            return list;
        }

        public void setList(GameDetailBean list) {
            this.list = list;
        }
    }
}
