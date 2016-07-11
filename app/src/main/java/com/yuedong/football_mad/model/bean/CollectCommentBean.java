package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/11
 */
public class CollectCommentBean {
    private String id;
    private String content;
    private String author;
    private String news;
    private String newstitle;
    private String authorname;
    private String authoravatar;
    private int authorlevel;
    private long createtime;

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAuthoravatar() {
        return authoravatar;
    }

    public void setAuthoravatar(String authoravatar) {
        this.authoravatar = authoravatar;
    }

    public int getAuthorlevel() {
        return authorlevel;
    }

    public void setAuthorlevel(int authorlevel) {
        this.authorlevel = authorlevel;
    }
}
