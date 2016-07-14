package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.HonorBean;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/14
 */
public class MyHonorAdapter extends BaseAdapter<HonorBean> {
    public boolean all = false;// 是所有荣誉
    public MyHonorAdapter(Context con, List<HonorBean> data) {
        super(con, data, R.layout.item_my_honor);
    }

    @Override
    public void convert(ViewHolder viewHolder, HonorBean honorBean, int position, View convertView) {
        View view = viewHolder.getIdByView(R.id.fl_view);
        TextView tvName = viewHolder.getIdByView(R.id.tv_honor);
        tvName.setText(honorBean.getName());
        ImageView ivLogo = viewHolder.getIdByView(R.id.iv_honor);
        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(honorBean.getLogo()),ivLogo);
        if(all){
            view.setBackgroundResource(R.drawable.bgtran_borderorange1dp_circle);
            tvName.getPaint().setFakeBoldText(false);
//            tvName
        }else {
            tvName.getPaint().setFakeBoldText(true);
            view.setBackgroundResource(R.drawable.bg_round_oranged6bc53a);
        }
    }
}
