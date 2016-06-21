package com.yuedong.football_mad.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/6
 */
public class TouchAdapter extends BaseAdapter<TouchBean> {
    public TouchAdapter(Context con, List<TouchBean> data) {
        super(con, data, R.layout.item_home_touch);
    }

    @Override
    public void convert(ViewHolder viewHolder, TouchBean bean, int position, View convertView) {
        viewHolder.getIdByView(R.id.item_top).setVisibility(View.VISIBLE);
        RoundImageView imageView = viewHolder.getIdByView(R.id.id_pic);
        ImageView icon = viewHolder.getIdByView(R.id.iv_icon);
        viewHolder.setText(R.id.tv_title,bean.getTitle()).setText(R.id.tv_content, bean.getDigest()).setText(R.id.tv_comment_num, bean.getComment());
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(bean.getThumbnail()), imageView);
        int type = bean.getType();
        ViewUtils.showLayout(icon);
        switch (type){
            case 2:
                icon.setImageResource(R.drawable.ic_yellow_jiandi);
                break;
            case 3:
                icon.setImageResource(R.drawable.ic_blue_qiutan);
                break;
            case 5:
                icon.setImageResource(R.drawable.ic_green_saiping);
                break;
            case 6:
                icon.setImageResource(R.drawable.ic_black_baike);
                break;
            default:
                ViewUtils.hideLayout(icon);
                break;
        }
    }
}
