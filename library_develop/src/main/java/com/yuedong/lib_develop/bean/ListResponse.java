package com.yuedong.lib_develop.bean;

import java.util.List;

public abstract class ListResponse<T> extends BaseResponse {
    private int totalcount;
    private int totalpage;


    public abstract List<T> getDataList();

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    @Override
    public String toString() {
        return "ListResponse{" +
                "totalcount=" + totalcount +
                ", totalpage=" + totalpage +
                '}';
    }
}
