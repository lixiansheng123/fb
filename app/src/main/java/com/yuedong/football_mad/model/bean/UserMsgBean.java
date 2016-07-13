package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class UserMsgBean {
    private String id;
    private String content;
    private String author;
    private String news;
    private String newstitle;
    private int newstype;
    private String authorname;
    private long createtime;
    private int authorlevel;

    public int getAuthorlevel() {
        return authorlevel;
    }

    public void setAuthorlevel(int authorlevel) {
        this.authorlevel = authorlevel;
    }

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

    public int getNewstype() {
        return newstype;
    }

    public void setNewstype(int newstype) {
        this.newstype = newstype;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }
}
