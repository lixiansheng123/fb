package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CollectCommentBean;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/11
 */
public class CollectCommentListAdapter extends BaseAdapter<CollectCommentBean> {
    private static final int PACK_UP = 1;
    private static final int UNFOLD = 2;
    public boolean isEdit;
    private View.OnClickListener deleteListener;
    public void setOnDeleteListener(View.OnClickListener deleteListener){
        this.deleteListener = deleteListener;
    }
    public CollectCommentListAdapter(Context con, List<CollectCommentBean> data) {
        super(con, data, R.layout.item_collect_comment_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, CollectCommentBean bean, int position, View convertView) {
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        ImageView ivDelete = viewHolder.getIdByView(R.id.iv_delete);
        final ImageView ivMore = viewHolder.getIdByView(R.id.iv_more);
        RoundImageView roundImageView = viewHolder.getIdByView(R.id.iv_head);
        TextView tvUserLevel = viewHolder.getIdByView(R.id.tv_level);
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        ImageView ivZan = viewHolder.getIdByView(R.id.iv_zan);
        int userlevel = bean.getAuthorlevel();
        String timeDesc = DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime() * 1000);
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userlevel);
        viewHolder.setText(R.id.tv_name, bean.getAuthorname())
        .setText(R.id.tv_title, bean.getNewstitle());
        tvContent.setText(bean.getContent());
        tvUserLevel.setText(TextUtils.addTextColor(userLevelDisplayInfo.textDesc + " " + timeDesc, 0, 2, userLevelDisplayInfo.partTextColor));
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAuthoravatar()), roundImageView);
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        ViewUtils.hideLayout(ivMore);
        ivMore.setTag(UNFOLD);
        ivMore.setImageResource(R.drawable.ic_blue_more_text);
        ivMore.setTag(R.drawable.ic_blue_more_text);
        ivMore.setOnClickListener(null);
        tvContent.setMaxLines(Integer.MAX_VALUE);
        tvContent.setTag(R.string.str_key_id, bean.getId());// 设置id-tag
        tvContent.setTag(R.string.str_key_position, position);
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

        if(isEdit){
            ViewUtils.showLayout(ivDelete);
            ivDelete.setTag(bean);
            ivDelete.setOnClickListener(deleteListener);
        }else{
            ViewUtils.hideLayout(ivDelete);
            ivDelete.setOnClickListener(null);
        }
    }
}
