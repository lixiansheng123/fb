package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/7
 */
public class CollectListRespBean extends ListResponse<CollectListBean> {
    private Data data;
    private List<CollectListBean> datas;

    public List<CollectListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectListBean> datas) {
        this.datas = datas;
    }

    @Override
    public List<CollectListBean> getDataList() {
        return datas;
    }

    @Override
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private DataList list;

        public DataList getList() {
            return list;
        }

        public void setList(DataList list) {
            this.list = list;
        }
    }

    public class DataList{
        private List<CollectListBean> news;
        private List<CollectListBean> special;

        public List<CollectListBean> getNews() {
            return news;
        }

        public void setNews(List<CollectListBean> news) {
            this.news = news;
        }

        public List<CollectListBean> getSpecial() {
            return special;
        }

        public void setSpecial(List<CollectListBean> special) {
            this.special = special;
        }
    }
}
