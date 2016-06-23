package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.FinalCompetitionBean;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuListAdapter extends BaseAdapter<FinalCompetitionBean> {
    public DataKuListAdapter(Context con, List<FinalCompetitionBean> data) {
        super(con, data,R.layout.item_dataku_competition);
    }

    @Override
    public void convert(ViewHolder viewHolder, FinalCompetitionBean finalCompetitionBean, int position, View convertView) {
        NetworkImageView imageView = viewHolder.getIdByView(R.id.iv_pic);
        View llLabel = viewHolder.getIdByView(R.id.ll_label);
        viewHolder.setText(R.id.tv_content, finalCompetitionBean.getAbbrname());
        TextView label = viewHolder.getIdByView(R.id.tv_label);
        DisplayImageByVolleyUtils.loadImage(imageView, UrlHelper.checkUrl(finalCompetitionBean.getLogo()));
        boolean hot = finalCompetitionBean.isHot();
        if(hot) {
            label.setBackgroundResource(R.drawable.bggreen_round3dp);
            label.setText("热门");
        }else{
            label.setBackgroundResource(R.drawable.bgblack757475_round3dp);
            label.setText("全部");
        }
        ViewUtils.showLayout(llLabel);
        if(position != 0){
            FinalCompetitionBean finalCompetitionBean1 = mDatas.get(position - 1);
            L.d(finalCompetitionBean1.toString());
            L.d(finalCompetitionBean.toString());
            if(finalCompetitionBean1.isHot() == finalCompetitionBean.isHot())
                ViewUtils.hideLayout(llLabel);
            else
                ViewUtils.showLayout(llLabel);
        }
    }
}
