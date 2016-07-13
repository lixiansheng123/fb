package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.InsightBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.NewsDetailActivity;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class InsihtAdapter extends BaseAdapter<InsightBean> {
    private boolean isHot = false;
    public void setIsHot(boolean isHot){this.isHot = isHot;}
    public InsihtAdapter(Context con, List<InsightBean> data) {
        super(con, data, R.layout.item_home_insight);
    }

    @Override
    public void convert(ViewHolder viewHolder, final InsightBean bean, int position, View convertView) {
        View topView = viewHolder.getIdByView(R.id.id_include_layout);
        ImageView topImageView = viewHolder.getIdByView(R.id.id_include_pic) ;
        View llPic = viewHolder.getIdByView(R.id.ll_pic);
        View line = viewHolder.getIdByView(R.id.line);
        topView.setBackgroundResource(android.R.color.white);
        if (position == 0) {
            ViewUtils.showLayout(topView);
            if(isHot){
                topImageView.setImageResource(R.drawable.ic_item_jiandi);
            }else{
                topImageView.setImageResource(R.drawable.ic_item_fabiao);
            }
        } else {
            ViewUtils.hideLayout(topView);
        }
        // main content。。。。。。
        RoundImageView imageView = viewHolder.getIdByView(R.id.id_pic);
        View headRl = viewHolder.getIdByView(R.id.id_item_head_layout);
        RoundImageView ivUserHead = viewHolder.getIdByView(R.id.iv_userhead);
        TextView tvUserLevel = viewHolder.getIdByView(R.id.tv_commentator);
        viewHolder.setText(R.id.tv_title, bean.getTitle())
                .setText(R.id.tv_content, bean.getDigest())
                .setText(R.id.tv_zan_num, bean.getGood())
                .setText(R.id.tv_comment_num, bean.getComment())
                .setText(R.id.tv_name, bean.getUsername())
                .setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime() * 1000));
        String url = UrlHelper.checkUrl(bean.getThumbnail());
        if(android.text.TextUtils.isEmpty(url)){
            ViewUtils.hideLayouts(llPic,line);
        }else {
            ViewUtils.showLayouts(llPic,line);
            DisplayImageByVolleyUtils.loadImage(url, imageView);
        }
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(bean.getAvatar()),ivUserHead);
        int userLevel = bean.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userLevel);
        SpannableStringBuilder spannableStringBuilder = TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor);
        tvUserLevel.setText(spannableStringBuilder);
        headRl.setBackgroundResource(userLevelDisplayInfo.headBg);

        // 点击实现
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_ID,bean.getId());
                LaunchWithExitUtils.startActivity((Activity)mCon, NewsDetailActivity.class,data);
            }
        });
        headRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR2,bean.getAuthorid());
                LaunchWithExitUtils.startActivity((Activity)mCon, UserInfoActivity.class,data);
            }
        });

    }
}
