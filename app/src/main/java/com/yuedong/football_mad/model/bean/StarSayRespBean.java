package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/30
 */
public class StarSayRespBean  extends ListResponse<StarSayBean>{
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public List<StarSayBean> getDataList() {
        return getData().getList();
    }

    @Override
    public Data getData(){
        return data;
    }

    public  class Data extends ListResponse.Data{
        private List<StarSayBean> list;

        public List<StarSayBean> getList() {
            return list;
        }

        public void setList(List<StarSayBean> list) {
            this.list = list;
        }
    }
}
