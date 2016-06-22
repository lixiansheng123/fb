package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.db.annotation.Column;
import com.yuedong.lib_develop.db.annotation.Id;
import com.yuedong.lib_develop.db.annotation.Table;

/**
 * @author 俊鹏 on 2016/6/22
 */
@Table(name = "searchRecord",execAfterTableCreated = "CREATE INDEX index_text ON searchRecord(content)")
public class SearchRecord {
    @Id
    private int id;
    @Column(column = "content")
    private String content;
    @Column(column = "time")
    private long time;
    @Column(column = "type")
    private int type; // 数据酷搜索

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
