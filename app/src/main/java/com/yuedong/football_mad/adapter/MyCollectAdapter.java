package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CollectListBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/20
 */
public class MyCollectAdapter extends BaseAdapter<CollectListBean> {
    public MyCollectAdapter(Context con, List<CollectListBean> data) {
        super(con, data, R.layout.item_my_collect);
    }

    @Override
    public void convert(ViewHolder viewHolder, final CollectListBean bean, int position, View convertView) {
        TextView tvLabel = viewHolder.getIdByView(R.id.tv_label);
        RelativeLayout rlLable = viewHolder.getIdByView(R.id.rl_top);
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_user_head);
        viewHolder.setText(R.id.tv_name, bean.getAuthorname())
                .setText(R.id.tv_content, bean.getTitle());
        int userlevel = bean.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userlevel);
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAvatar()), ivHead);
        int type = bean.type;
        if (type == 1) {
            tvLabel.setText("专题");
        } else {
            tvLabel.setText("文章");
        }
        ViewUtils.showLayout(rlLable);
        if (position != 0) {
            CollectListBean preBean = mDatas.get(position - 1);
            if (preBean.type == type) {
                ViewUtils.hideLayout(rlLable);
            }
        }
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_ID,bean.getId());
            }
        });

        rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR2,bean.getAuthorid());
                LaunchWithExitUtils.startActivity((Activity)mCon, UserInfoActivity.class,data);
            }
        });
    }
}
