package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.SixSixSixBean;
import com.yuedong.football_mad.ui.activity.NewsDetailActivity;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class SixSixSixCommentListAdapter extends BaseAdapter<SixSixSixBean.Comment> {
    public SixSixSixCommentListAdapter(Context con, List<SixSixSixBean.Comment> data) {
        super(con, data, R.layout.item_sixsixsix_comment_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, final SixSixSixBean.Comment comment, int position, View convertView) {
        ImageView ivLabel = viewHolder.getIdByView(R.id.iv_label);
        viewHolder.setText(R.id.tv_time,DateUtils.getDescriptionTimeFromTimestamp(comment.getCreatetime()*1000))
        .setText(R.id.tv_content,comment.getTitle())
        .setText(R.id.tv_zan_num,comment.getGood())
        .setText(R.id.tv_comment_num, comment.getComment());
        int type = comment.getType();
        int res = R.drawable.ic_label_jiandi;
        switch(type){
            case 3:
                res = R.drawable.ic_label_post;
                break;
            case 5:
                res = R.drawable.ic_label_saiping;
                break;
            case 6:
                res = R.drawable.ic_label_baike;
                break;
        }
        ivLabel.setImageResource(res);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString(Constant.KEY_ID,comment.getId());
                LaunchWithExitUtils.startActivity((Activity) mCon, NewsDetailActivity.class, data);
            }
        });
    }
}
