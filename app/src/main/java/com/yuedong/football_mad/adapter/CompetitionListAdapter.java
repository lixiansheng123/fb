package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DataKuBean;
import com.yuedong.football_mad.model.bean.FinalCompetitionBean;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/27
 */
public class CompetitionListAdapter extends BaseAdapter<FinalCompetitionBean> {
    public CompetitionListAdapter(Context con, List<FinalCompetitionBean> data) {
        super(con, data, R.layout.item_competition_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, FinalCompetitionBean dataKuBean, int position, View convertView) {
        viewHolder.setText(R.id.tv_content,dataKuBean.getAbbrname());
    }
}
