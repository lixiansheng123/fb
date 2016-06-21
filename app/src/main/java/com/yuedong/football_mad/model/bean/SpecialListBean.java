package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/8
 */
public class SpecialListBean extends ListResponse<SpecialBean> {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SpecialListBean{" +
                "data=" + data +
                '}';
    }

    @Override
    public List<SpecialBean> getDataList() {
        return getData().getList();
    }

    public class Data extends ListResponse.Data {
        private List<SpecialBean> list;

        public List<SpecialBean> getList() {
            return list;
        }

        public void setList(List<SpecialBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "list=" + list +
                    '}';
        }
    }
}
