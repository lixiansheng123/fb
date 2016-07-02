package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.DbLikeRecord;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class CommentListAdapter extends BaseAdapter<List<CommentBean>> {

    private static final int PACK_UP = 1;
    private static final int UNFOLD = 2;
    private SparseArray<SecondCommentListAdapter> adapters;
    private View.OnClickListener commmentClickListener;
    private View.OnClickListener zanClickLisenter;
    private DbUtils db;
    private List<DbLikeRecord> commentGoods;
    public void setOnZnClickListener(View.OnClickListener zanClickLisenter){
        this. zanClickLisenter = zanClickLisenter;
    }
    public void setOnCommentClickListener(View.OnClickListener listener) {
        this.commmentClickListener = listener;
    }

    public CommentListAdapter(Context con, List<List<CommentBean>> data) {
        super(con, data, R.layout.item_comment_list);
        adapters = new SparseArray<>();
        db = DbUtils.create(con);
        updateCommentGoods();

    }

    /**
     * 更新本地存储的点赞评论
     */
    public void updateCommentGoods(){
        try {
            commentGoods = db.findAll(Selector.from(DbLikeRecord.class).where("is_goods","=",1).and("like_type","=",1));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是点赞的
     * @return
     */
    private boolean isGoods(String id){
        if(commentGoods!=null && !commentGoods.isEmpty()){
            int commentId = Integer.parseInt(id);
            for(int i = 0;i < commentGoods.size(); i++){
                DbLikeRecord dbLikeRecord = this.commentGoods.get(i);
                if(commentId == dbLikeRecord.getComment_id())
                    return true;
            }
        }
        return false;
    }


    @Override
    public void convert(ViewHolder viewHolder, List<CommentBean> list, final int position, final View convertView) {
        if (list == null || list.isEmpty()) return;
        convertView.setClickable(false);
        CommentBean bean = list.get(list.size() - 1);
        SupportScrollConflictListView spListView = viewHolder.getIdByView(R.id.spListView);
        ViewUtils.hideLayout(spListView);
        // 二级评论
        if (list.size() > 1) {
            SecondCommentListAdapter adapter = adapters.get(position);
            if (adapter == null) {
                adapter = new SecondCommentListAdapter(mCon);
                adapters.put(position, adapter);
            }
            spListView.setAdapter(adapter);
            ViewUtils.showLayout(spListView);
            List<CommentBean> sencondCommentList = new ArrayList<>(list);
            int lastIndex = list.size() - 1;
            sencondCommentList.remove(lastIndex);
            if (sencondCommentList.size() > 5) {
                List<CommentBean> partData = new ArrayList<>();
                partData.add(sencondCommentList.get(0));
                partData.add(sencondCommentList.get(1));
                partData.add(sencondCommentList.get(sencondCommentList.size() - 1));
                adapter.setFullData(sencondCommentList);
                adapter.setData(partData);
                adapter.notifyDataSetChanged();
            } else {
                adapter.setData(sencondCommentList);
                adapter.notifyDataSetChanged();
            }
        }
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        final ImageView ivMore = viewHolder.getIdByView(R.id.iv_more);
        RoundImageView roundImageView = viewHolder.getIdByView(R.id.iv_head);
        TextView tvUserLevel = viewHolder.getIdByView(R.id.tv_level);
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        ImageView ivZan = viewHolder.getIdByView(R.id.iv_zan);
        int userlevel = bean.getUserlevel();
        String timeDesc = DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime() * 1000);
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
        tvContent.setTag(R.string.str_key_id, bean.getId());// 设置id-tag
        tvContent.setTag(R.string.str_key_position, position);
        // 内容click事件
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commmentClickListener != null)
                    commmentClickListener.onClick(v);
            }
        });
        // 赞
        if(isGoods(bean.getId())){
            ivZan.setImageResource(R.drawable.ic_big_blue_zan);
            ivZan.setTag(true);
        } else {
            ivZan.setImageResource(R.drawable.ic_big_white_zan);
            ivZan.setTag(false);
        }
        ivZan.setTag(R.string.str_key_position,position);
        ivZan.setTag(R.string.str_key_view,convertView);
        ivZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zanClickLisenter != null)
                    zanClickLisenter.onClick(v);
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
            }
        });

    }
}
