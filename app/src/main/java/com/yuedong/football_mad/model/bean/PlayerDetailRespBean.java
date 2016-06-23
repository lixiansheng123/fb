package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

/**
 * @author 俊鹏 on 2016/6/23
 */
public class PlayerDetailRespBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public  class Data{
        private PlayerDetailBean list;

        public PlayerDetailBean getList() {
            return list;
        }

        public void setList(PlayerDetailBean list) {
            this.list = list;
        }
    }
}
