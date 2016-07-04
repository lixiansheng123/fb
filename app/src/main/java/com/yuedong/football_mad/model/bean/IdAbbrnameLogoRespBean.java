package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/4
 */
public class IdAbbrnameLogoRespBean extends ListResponse<IdAbbrnameLogoBean> {
    private Data data;

    @Override
    public List<IdAbbrnameLogoBean> getDataList() {
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
        public List<IdAbbrnameLogoBean> list;

        public List<IdAbbrnameLogoBean> getList() {
            return list;
        }

        public void setList(List<IdAbbrnameLogoBean> list) {
            this.list = list;
        }
    }
}
