package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DataKuBean;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/23
 */
public class TeamHeaderAdapter extends BaseAdapter<DataKuBean> {
    public TeamHeaderAdapter(Context con, List<DataKuBean> data) {
        super(con, data, R.layout.item_dataku_team_header);
    }

    @Override
    public void convert(ViewHolder viewHolder, DataKuBean dataKuBean, int position, View convertView) {
        viewHolder.setText(R.id.tv_name,dataKuBean.getAbbrname());
        NetworkImageView imageView = viewHolder.getIdByView(R.id.iv_pic);
        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(dataKuBean.getLogo()),imageView);
    }
}
