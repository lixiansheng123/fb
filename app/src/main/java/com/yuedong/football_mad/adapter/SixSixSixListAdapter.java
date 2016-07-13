package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.SixSixSixBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.NewsDetailActivity;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.view.RoundImageView;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class SixSixSixListAdapter extends BaseAdapter<SixSixSixBean> {
    public SixSixSixListAdapter(Context con, List<SixSixSixBean> data) {
        super(con, data, R.layout.item_sixsixsix_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, final SixSixSixBean sixSixSixBean, int position, View convertView) {
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_user_head);
        TextView tvName = viewHolder.getIdByView(R.id.tv_name);
        SupportScrollConflictListView listView = viewHolder.getIdByView(R.id.spListView);
        viewHolder.setText(R.id.tv_zan_num,sixSixSixBean.getPartnewsgood());
        int userLevel = sixSixSixBean.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userLevel);
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(sixSixSixBean.getAvatar()), ivHead);
        SpannableStringBuilder ss = TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor);
        tvName.setText(ss);
        // comment list
        List<SixSixSixBean.Comment> news = sixSixSixBean.getNews();
        SixSixSixCommentListAdapter adapter = new SixSixSixCommentListAdapter(mCon,news);
        listView.setAdapter(adapter);

//        // 点击实现

        rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR2, sixSixSixBean.getAuthorid());
                LaunchWithExitUtils.startActivity((Activity) mCon, UserInfoActivity.class, data);
            }
        });
    }
}
