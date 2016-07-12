package com.yuedong.football_mad.model.bean;

import com.yuedong.lib_develop.db.annotation.Column;
import com.yuedong.lib_develop.db.annotation.Id;
import com.yuedong.lib_develop.db.annotation.Table;

/**
 * @author 俊鹏 on 2016/7/12
 */
@Table(name = "look_friend",execAfterTableCreated = "CREATE INDEX index_look_friend_id ON look_friend(friend_id,user_id)")
public class DbLookFriendBean {
    @Id
    private int id;
    @Column(column = "friend_id")
    private String friendId;
    @Column(column = "user_id")
    private String userId;
    @Column(column = "updatea_article_time")
    private long updateArticleTime;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getUpdateArticleTime() {
        return updateArticleTime;
    }

    public void setUpdateArticleTime(long updateArticleTime) {
        this.updateArticleTime = updateArticleTime;
    }
}
