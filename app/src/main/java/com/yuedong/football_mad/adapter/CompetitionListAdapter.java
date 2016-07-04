package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.FinalCompetitionBean;
import com.yuedong.football_mad.model.bean.IdAbbrnameLogoBean;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/27
 */
public class CompetitionListAdapter extends BaseAdapter<IdAbbrnameLogoBean> {
    private int selPos = -1;
    public void setSelPos(int pos){
        this.selPos = pos;
        notifyDataSetChanged();
    }
    public CompetitionListAdapter(Context con, List<IdAbbrnameLogoBean> data) {
        super(con, data, R.layout.item_competition_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, IdAbbrnameLogoBean bean, int position, View convertView) {
        View sel = viewHolder.getIdByView(R.id.sel);
        viewHolder.setText(R.id.tv_content,bean.getAbbrname());
        if(selPos == position){
            ViewUtils.showLayout(sel);
        }else {
            ViewUtils.hideLayout(sel);
        }
    }
}
