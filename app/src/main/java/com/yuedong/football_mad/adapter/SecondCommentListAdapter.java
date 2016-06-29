package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊鹏 on 2016/6/29
 */
public class SecondCommentListAdapter extends BaseAdapter<CommentBean> {
    private List<CommentBean> fullData;
    public SecondCommentListAdapter(Context con) {
        super(con, R.layout.item_second_comment);
    }

    public void setFullData( List<CommentBean> fullData){
        this.fullData = fullData;
    }

    @Override
    public void convert(ViewHolder viewHolder, CommentBean commentBean, int position, final View convertView) {
        final TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        View llOpenLevel = viewHolder.getIdByView(R.id.ll_open_level);
        viewHolder.setText(R.id.tv_number, position + 1 + "")
                .setText(R.id.tv_name, commentBean.getUsername())
                .setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(commentBean.getCreatetime() * 1000));
        tvContent.setText(commentBean.getContent());
        ViewUtils.hideLayout(viewHolder.getIdByView(R.id.iv_more));
        ViewUtils.hideLayout(llOpenLevel);
        llOpenLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               List<CommentBean> data = new ArrayList<CommentBean>(fullData);
                fullData = null;
                setData(data);
                notifyDataSetChanged();
            }
        });
        if(fullData!=null && !fullData.isEmpty()){
            if(position == getCount()-1){
                viewHolder.setText(R.id.tv_number,fullData.size()+"");
            }
           if(position == 1){
               ViewUtils.showLayout(llOpenLevel);
           }
        }
    }
}
