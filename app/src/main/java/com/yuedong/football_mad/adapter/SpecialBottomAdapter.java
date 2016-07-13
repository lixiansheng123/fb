package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.SpecialBean;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.SpecialDetailActivity;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class SpecialBottomAdapter extends BaseAdapter<SpecialBean> {
    public SpecialBottomAdapter(Context con, List<SpecialBean> data) {
        super(con, data, R.layout.item_home_special2);
    }

    @Override
    public void convert(ViewHolder viewHolder, final SpecialBean bean, int position, View convertView) {
        NetworkImageView pic = viewHolder.getIdByView(R.id.iv_pic);
        RoundImageView userHead = viewHolder.getIdByView(R.id.iv_user_head);
        TextView levelDesc = viewHolder.getIdByView(R.id.tv_level);
        viewHolder.setText(R.id.tv_title, bean.getName()).setText(R.id.tv_desc, bean.getRemark()).setText(R.id.tv_user_nick, bean.getAuthor());
        DisplayImageByVolleyUtils.loadImage(pic, UrlHelper.checkUrl(bean.getPic()));
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(bean.getAvatar()), userHead);
        // 处理等级
        Integer userlevel = bean.getUserlevel();
        DisplayUserLevelBean userLevelBean = CommonHelper.getUserLevelDisplayInfo(userlevel);
        levelDesc.setText(TextUtils.addTextColor(userLevelBean.textDesc, 0, 2, userLevelBean.partTextColor));;
        viewHolder.getIdByView(R.id.rl_user_level).setBackgroundResource(userLevelBean.headBg);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_ID, bean.getId());
                LaunchWithExitUtils.startActivity((Activity) mCon, SpecialDetailActivity.class, bundle);
            }
        });
        viewHolder.getIdByView(R.id.rl_user_level).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_STR2, bean.getAuthorid());
                LaunchWithExitUtils.startActivity((Activity) mCon, UserInfoActivity.class, bundle);
            }
        });
    }
}
