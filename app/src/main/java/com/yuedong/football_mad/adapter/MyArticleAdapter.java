package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.ui.activity.NewsDetailActivity;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/20
 */
public class MyArticleAdapter extends BaseAdapter<TouchBean> {


    public MyArticleAdapter(Context con, List<TouchBean> data) {
        super(con, data, R.layout.item_my_article);
    }

    @Override
    public void convert(ViewHolder viewHolder, final TouchBean bean, int position, View convertView) {
        viewHolder.setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime()*1000))
        .setText(R.id.tv_title2,bean.getTitle())
        .setText(R.id.tv_zan_num,bean.getGood())
        .setText(R.id.tv_comment_num,bean.getComment());
        viewHolder.setImageResource(R.id.iv_icon, CommonHelper.getNewsTypeIcon(bean.getType()));
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_ID,bean.getId());
                LaunchWithExitUtils.startActivity((Activity)mCon, NewsDetailActivity.class,data);
            }
        });
    }
}
