package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.SearchRecord;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuSearchAdapter extends BaseAdapter<SearchRecord> {
    public DataKuSearchAdapter(Context con) {
        super(con, R.layout.item_dataku_search);
    }

    @Override
    public void convert(ViewHolder viewHolder, SearchRecord searchRecord, int position, View convertView) {
        viewHolder.setText(R.id.tv_content,searchRecord.getContent());
    }
}
