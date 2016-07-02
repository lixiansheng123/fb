package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.db.annotation.Column;
import com.yuedong.lib_develop.db.annotation.Id;
import com.yuedong.lib_develop.db.annotation.Table;

/**
 * @author 俊鹏 on 2016/6/30
 */
@Table(name = "like_record",execAfterTableCreated = "CREATE INDEX index_like_record_id ON like_record(comment_id,like_type)")
public class DbLikeRecord {
    @Id
    private int id;
    @Column(column = "comment_id")
    private int comment_id;
    @Column(column = "like_type")
    private int like_type = 1;// 类型 1 评论 2 新闻
    @Column(column = "is_goods")
    private int is_goods; // 1为点赞 2为取消点赞 3踩

    public int getLike_type() {
        return like_type;
    }

    public void setLike_type(int like_type) {
        this.like_type = like_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getIs_goods() {
        return is_goods;
    }

    public void setIs_goods(int is_goods) {
        this.is_goods = is_goods;
    }

    @Override
    public String toString() {
        return "DbLikeRecord{" +
                "id=" + id +
                ", comment_id=" + comment_id +
                ", like_type=" + like_type +
                ", is_goods=" + is_goods +
                '}';
    }
}
