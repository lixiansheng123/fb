package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class AttentionStarBean {
    private String id;
    private String avatar;
    private String name;
    private String interest = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
