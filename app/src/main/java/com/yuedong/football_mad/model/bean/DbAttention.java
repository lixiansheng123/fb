package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.db.annotation.Column;
import com.yuedong.lib_develop.db.annotation.Table;

/**
 * 关注bean
 * @author 俊鹏 on 2016/7/4
 */
@Table(name = "attention",execAfterTableCreated = "CREATE INDEX index_id ON attention(id,type)")
public class DbAttention {
    @Column(column = "id")
    private String id;
    @Column(column = "time")
    private long attentionTime;
    @Column(column = "type")
    private int type;// 1 比赛

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAttentionTime() {
        return attentionTime;
    }

    public void setAttentionTime(long attentionTime) {
        this.attentionTime = attentionTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
