package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/12
 */
public class IdAvatarNameUserLeveLastPublishTimelBean {
    private String id;
    private String avatar;
    private String name;
    private int userlevel;
    private long lastpublishtime;

    public long getLastpublishtime() {
        return lastpublishtime;
    }

    public void setLastpublishtime(long lastpublishtime) {
        this.lastpublishtime = lastpublishtime;
    }

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

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }
}
