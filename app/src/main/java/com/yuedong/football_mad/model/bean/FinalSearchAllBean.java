package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/6/24
 */
public class FinalSearchAllBean extends SearchAllBean{
    private int type;// 1赛事 2球队 3球员 4国家 5其他

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
