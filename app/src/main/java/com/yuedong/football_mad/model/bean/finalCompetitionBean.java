package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class FinalCompetitionBean extends DataKuBean {
    private boolean isHot;

    public boolean isHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    @Override
    public String toString() {
        return "FinalCompetitionBean{" +
                "isHot=" + isHot +
                '}';
    }
}
