package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.bean.ListResponse;

import java.util.List;

public class ListUser extends ListResponse<User> {
    public List<User> list;

    public void setList(List<User> list) {
        this.list = list;
    }

    public List<User> getList() {
        return list;
    }

    @Override
    public List<User> getDataList() {
        return null;
    }

    @Override
    public Data getData() {
        return null;
    }

    @Override
    public String toString() {
        return "ListUser{" +
                "list=" + list +
                '}';
    }
}
