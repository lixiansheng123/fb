package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.MyCommentBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class MyCommentListAdapter extends BaseAdapter<MyCommentBean> {
    private static final int PACK_UP = 1;
    private static final int UNFOLD = 2;
    public MyCommentListAdapter(Context con, List<MyCommentBean> data) {
        super(con, data, R.layout.item_my_comment_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, MyCommentBean bean, int position, View convertView) {
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        final ImageView ivMore = viewHolder.getIdByView(R.id.iv_more);
        viewHolder.setText(R.id.tv_title,bean.getNewstitle())
        .setText(R.id.tv_zan_num,"  "+bean.getGood())
        .setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime()*1000));
        tvContent.setText(bean.getContent());
        tvContent.setMaxLines(Integer.MAX_VALUE);
        ivMore.setTag(UNFOLD);
        ivMore.setImageResource(R.drawable.ic_blue_more_text);
        ivMore.setOnClickListener(null);
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
