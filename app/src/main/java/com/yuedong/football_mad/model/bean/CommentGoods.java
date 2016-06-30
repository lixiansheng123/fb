package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.db.annotation.Column;
import com.yuedong.lib_develop.db.annotation.Id;
import com.yuedong.lib_develop.db.annotation.Table;

/**
 * @author 俊鹏 on 2016/6/30
 */
@Table(name = "comment_goods",execAfterTableCreated = "CREATE INDEX index_comment_id ON comment_goods(comment_id)")
public class CommentGoods {
    @Id
    private int id;
    @Column(column = "comment_id")
    private int comment_id;
    @Column(column = "is_goods")
    private int is_goods; // 1为点赞 2为取消点赞

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
}
