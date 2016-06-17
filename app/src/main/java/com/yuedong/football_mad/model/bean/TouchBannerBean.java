package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/6/8
 */
public class TouchBannerBean {
    private String id;
    private String newsid;
    private String pic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "TouchBannerBean{" +
                "id='" + id + '\'' +
                ", newsid='" + newsid + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
