package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/23
 */
public class SearchAllRespBean extends ListResponse<SearchAllBean> {
    private Data data;

    @Override
    public java.util.List<SearchAllBean> getDataList() {
        return null;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends ListResponse.Data{
        private List list;

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }
    }

    public class List{
        private java.util.List<SearchAllBean> athlete;
        private java.util.List<SearchAllBean> country;
        private java.util.List<SearchAllBean> game;
        private java.util.List<SearchAllBean> team;
        private java.util.List<SearchAllBean> other;

        public java.util.List<SearchAllBean> getAthlete() {
            return athlete;
        }

        public void setAthlete(java.util.List<SearchAllBean> athlete) {
            this.athlete = athlete;
        }

        public java.util.List<SearchAllBean> getCountry() {
            return country;
        }

        public void setCountry(java.util.List<SearchAllBean> country) {
            this.country = country;
        }

        public java.util.List<SearchAllBean> getGame() {
            return game;
        }

        public void setGame(java.util.List<SearchAllBean> game) {
            this.game = game;
        }

        public java.util.List<SearchAllBean> getTeam() {
            return team;
        }

        public void setTeam(java.util.List<SearchAllBean> team) {
            this.team = team;
        }

        public java.util.List<SearchAllBean> getOther() {
            return other;
        }

        public void setOther(java.util.List<SearchAllBean> other) {
            this.other = other;
        }
    }
}
