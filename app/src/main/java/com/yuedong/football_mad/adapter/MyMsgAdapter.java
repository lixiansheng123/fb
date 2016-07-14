package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.bean.UserMsgBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/20
 */
public class MyMsgAdapter extends BaseAdapter<UserMsgBean> {
    private static final int PACK_UP = 1;
    private static final int UNFOLD = 2;
    public MyMsgAdapter(Context con, List<UserMsgBean> data) {
        super(con, data, R.layout.item_my_msg);
    }

    @Override
    public void convert(ViewHolder viewHolder, final UserMsgBean bean, int position, View convertView) {
        // TODO 这里缺少两个时间
        ImageView ivIcon = viewHolder.getIdByView(R.id.iv_icon);
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        final ImageView ivMore = viewHolder.getIdByView(R.id.iv_more);
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_head);
        TextView tvLevel = viewHolder.getIdByView(R.id.tv_level);
        ivIcon.setImageResource(CommonHelper.getNewsTypeIcon(bean.getNewstype()));
        viewHolder.setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime() * 1000))
                .setText(R.id.tv_title2,bean.getNewstitle())
        .setText(R.id.tv_name, bean.getAuthorname());
        tvContent.setText(bean.getContent());
        DisplayUserLevelBean userLevelBean = CommonHelper.getUserLevelDisplayInfo(bean.getAuthorlevel());
        rlHead.setBackgroundResource(userLevelBean.headBg);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAuthor()), ivHead);
        tvLevel.setText(TextUtils.addTextColor(userLevelBean.textDesc + " 5小时前", 0, 2, userLevelBean.partTextColor));
        ViewUtils.hideLayout(ivMore);
        ivMore.setTag(UNFOLD);
        ivMore.setImageResource(R.drawable.ic_blue_more_text);
        // 评论内容过长特殊处理
        tvContent.setMaxLines(Integer.MAX_VALUE);
        tvContent.post(new Runnable() {
            @Override
            public void run() {
                int lineCount = tvContent.getLineCount();
                if (lineCount > 3) {
                    tvContent.setMaxLines(3);
                    ViewUtils.showLayouts(ivMore);
                    ivMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int state = (int) v.getTag();
                            if (state == UNFOLD) {
                                v.setTag(PACK_UP);
                                ivMore.setImageResource(R.drawable.ic_blue_shouqi);
                                tvContent.setMaxLines(Integer.MAX_VALUE);
                            } else {
                                v.setTag(UNFOLD);
                                ivMore.setImageResource(R.drawable.ic_blue_more_text);
                                tvContent.setMaxLines(3);
                            }
                        }
                    });
                } else {
                    tvContent.setMaxLines(Integer.MAX_VALUE);
                    ViewUtils.hideLayout(ivMore);
                }
            }
        });

        rlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(Constant.KEY_STR2, bean.getAuthor());
//                b.putString(Constant.KEY_STR, bean.getUsername());
                LaunchWithExitUtils.startActivity((Activity) mCon, UserInfoActivity.class, b);
            }
        });
    }
}
