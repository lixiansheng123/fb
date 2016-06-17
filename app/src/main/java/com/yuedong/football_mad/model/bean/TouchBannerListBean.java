package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;

import java.util.List;

/**
 * 焦点模块banner列表
 *
 * @author 俊鹏 on 2016/6/8
 */
public class TouchBannerListBean extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private List<TouchBannerBean> horlist;
        private List<TouchBannerBean> verlist;

        public List<TouchBannerBean> getVerlist() {
            return verlist;
        }

        public void setVerlist(List<TouchBannerBean> verlist) {
            this.verlist = verlist;
        }

        public List<TouchBannerBean> getHorlist() {
            return horlist;
        }

        public void setHorlist(List<TouchBannerBean> horlist) {
            this.horlist = horlist;
        }

        @Override
        public String toString() {
            return "TouchBannerListBean{" +
                    "horlist=" + horlist +
                    ", verlist=" + verlist +
                    '}';
        }
    }
}
