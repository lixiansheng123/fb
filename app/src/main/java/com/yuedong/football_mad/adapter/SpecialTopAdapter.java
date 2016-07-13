package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.IdNameLogoBean;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.SpecialDetailActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class SpecialTopAdapter extends BaseAdapter<IdNameLogoBean> {
    public SpecialTopAdapter(Context con, List<IdNameLogoBean> data) {
        super(con, data, R.layout.item_home_special);
    }

    @Override
    public void convert(ViewHolder viewHolder, final IdNameLogoBean bean, final int position, View convertView) {
        viewHolder.setText(R.id.tv_name, bean.getName());
        ImageView logo = viewHolder.getIdByView(R.id.iv_logo);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(bean.getLogo()), logo);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_ID,bean.getId());
                LaunchWithExitUtils.startActivity((Activity)mCon, SpecialDetailActivity.class, bundle);
            }
        });
    }
}
