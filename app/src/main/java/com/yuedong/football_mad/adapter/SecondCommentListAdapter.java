package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CommentBean;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/29
 */
public class SecondCommentListAdapter extends BaseAdapter<CommentBean> {
    public SecondCommentListAdapter(Context con, List<CommentBean> data) {
        super(con, data, R.layout.item_second_comment);
    }

    @Override
    public void convert(ViewHolder viewHolder, CommentBean commentBean, int position, View convertView) {

    }
}
