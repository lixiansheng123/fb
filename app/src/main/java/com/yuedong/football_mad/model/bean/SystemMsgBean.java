package com.yuedong.football_mad.model.bean;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class SystemMsgBean {
    private String id;
    private String content;
    private long createtime;

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

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
