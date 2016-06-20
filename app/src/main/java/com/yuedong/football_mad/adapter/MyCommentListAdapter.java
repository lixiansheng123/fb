package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.User;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class MyCommentListAdapter extends BaseAdapter<User> {
    public MyCommentListAdapter(Context con, List<User> data) {
        super(con, data, R.layout.item_my_comment_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, User user, int position, View convertView) {

    }
}
