package com.yuedong.football_mad.model.bean;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class SixSixSixBean {
    private String name;
    private String avatar;
    private int userlevel;
    private String partnewsgood;
    private String authorid;
    private List<Comment> news;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public String getPartnewsgood() {
        return partnewsgood;
    }

    public void setPartnewsgood(String partnewsgood) {
        this.partnewsgood = partnewsgood;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public List<Comment> getNews() {
        return news;
    }

    public void setNews(List<Comment> news) {
        this.news = news;
    }

    public class Comment{
        private String id;
        private String title;
        private int type;
        private String good;
        private String comment;
        private long createtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGood() {
            return good;
        }

        public void setGood(String good) {
            this.good = good;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }
    }
}
