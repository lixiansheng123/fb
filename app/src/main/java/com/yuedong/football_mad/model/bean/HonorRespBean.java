package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/14
 */
public class HonorRespBean extends BaseResponse{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private List<HonorBean> list;

        public List<HonorBean> getList() {
            return list;
        }

        public void setList(List<HonorBean> list) {
            this.list = list;
        }
    }
}
