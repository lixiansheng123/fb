package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.bean.IdNameLogoBean;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class SpecialTopAdapter extends BaseAdapter<IdNameLogoBean> {
    public SpecialTopAdapter(Context con, List<IdNameLogoBean> data) {
        super(con, data, R.layout.item_home_special);
    }

    @Override
    public void convert(ViewHolder viewHolder, IdNameLogoBean bean, int position, View convertView) {
        viewHolder.setText(R.id.tv_name, bean.getName());
        ImageView logo = viewHolder.getIdByView(R.id.iv_logo);
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(bean.getLogo()), logo);
    }
}
