package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.LoginActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class CommentListAdapter extends BaseAdapter<List<CommentBean>> {
    private static final int PACK_UP = 1;
    private static final int UNFOLD = 2;
    public CommentListAdapter(Context con, List<List<CommentBean>> data) {
        super(con, data, R.layout.item_comment_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, List<CommentBean> list, final int position, final View convertView) {
        convertView.setVisibility(View.INVISIBLE);
        if(list == null || list.isEmpty())return;
        convertView.setClickable(false);
        CommentBean bean = list.get(0);
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        final ImageView ivMore = viewHolder.getIdByView(R.id.iv_more);
        RoundImageView roundImageView = viewHolder.getIdByView(R.id.iv_head);
        TextView tvUserLevel = viewHolder.getIdByView(R.id.tv_level);
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        ImageView ivZan = viewHolder.getIdByView(R.id.iv_zan);
        int userlevel = bean.getUserlevel();
        String timeDesc = DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime()*1000);
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userlevel);
        viewHolder.setText(R.id.tv_name, bean.getUsername())
        .setText(R.id.tv_zan_num, bean.getGood());
        tvContent.setText(bean.getContent());
        tvUserLevel.setText(TextUtils.addTextColor(userLevelDisplayInfo.textDesc + " " + timeDesc, 0, 2, userLevelDisplayInfo.partTextColor));
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAvatar()), roundImageView);
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        ViewUtils.hideLayout(ivMore);
        ivMore.setTag(UNFOLD);
        ivMore.setImageResource(R.drawable.ic_blue_more_text);
        ivMore.setTag(R.drawable.ic_blue_more_text);
        ivMore.setOnClickListener(null);
        tvContent.setMaxLines(Integer.MAX_VALUE);
        // 赞
        ivZan.setTag(position);
        ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loginUser = MyApplication.getInstance().getLoginUser();
                if(loginUser == null) {
                    LaunchWithExitUtils.startActivity((Activity) mCon, LoginActivity.class);
                    return;
                }
                Map<String, String> post = new HashMap<>();
                post.put("id",mDatas.get(position).get(0).getId());
                post.put("userid",loginUser.getId());
                RequestHelper.post(Constant.URL_COMMENT_ZAN, post, null, false, false, new VolleyNetWorkCallback() {
                    @Override
                    public void onNetworkStart(String tag) {

                    }

                    @Override
                    public void onNetworkSucceed(String tag, BaseResponse data) {

                    }

                    @Override
                    public void onNetworkError(String tag, VolleyError error) {

                    }
                });
            }
        });
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
                convertView.setVisibility(View.VISIBLE);
            }
        });
    }
}
