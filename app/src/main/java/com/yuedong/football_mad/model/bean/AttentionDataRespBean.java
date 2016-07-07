package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/6
 */
public class AttentionDataRespBean extends ListResponse<FinalSearchAllBean> {
    private SearchAllRespBean.Data data;
    private List<FinalSearchAllBean> list2;

    public List<FinalSearchAllBean> getList2() {
        return list2;
    }

    public void setList2(List<FinalSearchAllBean> list2) {
        this.list2 = list2;
    }

    @Override
    public List<FinalSearchAllBean> getDataList() {
        return list2;
    }

    @Override
    public SearchAllRespBean.Data getData() {
        return data;
    }


}
