package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.model.bean.NewWithUserInfoBean;
import com.yuedong.football_mad.ui.activity.NewsDetailActivity;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/14
 */
public class BallPlayerAdapter extends BaseAdapter<NewWithUserInfoBean> {
    public BallPlayerAdapter(Context con, List<NewWithUserInfoBean> data) {
        super(con, data, R.layout.item_ballplayer);
    }

    @Override
    public void convert(ViewHolder viewHolder, final NewWithUserInfoBean newWithUserInfoBean, int position, View convertView) {
        TextView tvLable = viewHolder.getIdByView(R.id.tv_label);
        View rlTop = viewHolder.getIdByView(R.id.rl_top);
        View rlHead = viewHolder.getIdByView(R.id.id_item_head_layout);
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_userhead);
        NetworkImageView ivPic = viewHolder.getIdByView(R.id.iv_pic);
        TextView tvLevel = viewHolder.getIdByView(R.id.tv_commentator);
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(newWithUserInfoBean.getUserlevel());
        tvLevel.setText(com.yuedong.lib_develop.utils.TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor));
        viewHolder.setText(R.id.tv_name,newWithUserInfoBean.getUsername())
        .setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(newWithUserInfoBean.getCreatetime()*1000))
        .setText(R.id.tv_comment_num,newWithUserInfoBean.getComment())
        .setText(R.id.tv_title, newWithUserInfoBean.getTitle());
        String url = newWithUserInfoBean.getThumbnail();
        if(TextUtils.isEmpty(url)){
            ViewUtils.hideLayout(ivPic);
        }else {
            ViewUtils.showLayout(ivPic);
            DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(url),ivPic);
        }
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(newWithUserInfoBean.getAvatar()),ivHead);

        // label控制
        if(newWithUserInfoBean.isHot()){
            tvLable.setText("热门");
        }else{
            tvLable.setText("最新");
        }
        if(position != 0){
            NewWithUserInfoBean withUserInfoBean = mDatas.get(position - 1);
            if(withUserInfoBean.isHot() == newWithUserInfoBean.isHot()){
                ViewUtils.hideLayout(rlTop);
            }else{
                ViewUtils.showLayout(rlTop);
            }
        }

        // click envent
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_ID,newWithUserInfoBean.getId());
                LaunchWithExitUtils.startActivity((Activity)mCon, NewsDetailActivity.class,data);
            }
        });
        rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR2,newWithUserInfoBean.getAuthorid());
                LaunchWithExitUtils.startActivity((Activity)mCon, UserInfoActivity.class,data);
            }
        });
    }
}
