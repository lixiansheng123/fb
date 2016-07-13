package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.SystemMsgBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.lib_develop.utils.DateUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class SystemMsgAdapter extends BaseAdapter<SystemMsgBean> {
    public SystemMsgAdapter(Context con, List<SystemMsgBean> data) {
        super(con, data, R.layout.item_system_msg);
    }

    @Override
    public void convert(ViewHolder viewHolder, SystemMsgBean systemMsgBean, int position, View convertView) {
        viewHolder.setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(systemMsgBean.getCreatetime()*1000))
        .setText(R.id.tv_title2,systemMsgBean.getContent());
        ImageView ivIcon = viewHolder.getIdByView(R.id.iv_icon);
        ivIcon.setImageResource(R.drawable.ic_label_guanfang);

    }
}
