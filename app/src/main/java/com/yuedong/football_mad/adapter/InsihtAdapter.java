package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class InsihtAdapter extends BaseAdapter<User> {
    public InsihtAdapter(Context con, List<User> data) {
        super(con, data, R.layout.item_home_insight);
    }

    @Override
    public void convert(ViewHolder viewHolder, User user, int position, View convertView) {
        View topView = viewHolder.getIdByView(R.id.id_include_layout);
        viewHolder.setImageResource(R.id.id_pic, R.drawable.haizeiwang);
        if (position == 0) {
            topView.setBackgroundResource(R.color.greyE5E5E5);
        } else {
            topView.setBackgroundResource(android.R.color.white);
        }
    }
}
