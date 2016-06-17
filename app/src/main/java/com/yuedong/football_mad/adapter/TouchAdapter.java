package com.yuedong.football_mad.adapter;


import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.lib_develop.utils.L;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/6
 */
public class TouchAdapter extends BaseAdapter<User> {
    public TouchAdapter(Context con, List<User> data) {
        super(con, data, R.layout.item_home_touch);
    }

    @Override
    public void convert(ViewHolder viewHolder, User user, int position, View convertView) {
        L.d("adapter build itme.....");
        viewHolder.getIdByView(R.id.item_top).setVisibility(View.VISIBLE);
    }
}
