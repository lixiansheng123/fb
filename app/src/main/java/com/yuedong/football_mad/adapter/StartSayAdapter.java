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
 * @author 俊鹏 on 2016/6/7
 */
public class StartSayAdapter extends BaseAdapter<User> {
    public StartSayAdapter(Context con, List<User> data) {
        super(con, data, R.layout.item_home_star_say);
    }

    @Override
    public void convert(ViewHolder viewHolder, User user, int position, View convertView) {
        L.d("pass by...adapter");
    }
}
