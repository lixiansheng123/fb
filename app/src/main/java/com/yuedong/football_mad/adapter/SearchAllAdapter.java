package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.FinalSearchAllBean;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/24
 */
public class SearchAllAdapter extends BaseAdapter<FinalSearchAllBean> {

    public SearchAllAdapter(Context con) {
        super(con, R.layout.item_dataku_competition);
    }

    @Override
    public void convert(ViewHolder viewHolder, FinalSearchAllBean finalSearchAllBean, int position, View convertView) {
        View llLable = viewHolder.getIdByView(R.id.ll_label);
        TextView tvLabel = viewHolder.getIdByView(R.id.tv_label);
        TextView tvContent = viewHolder.getIdByView(R.id.tv_content);
        NetworkImageView imageView = viewHolder.getIdByView(R.id.iv_pic);
        int type = finalSearchAllBean.getType();
        String lable = "";
        switch (type){
            // 赛事
            case 1:lable = "赛事";break;
            // 球队
            case 2:lable = "球队";break;
            // 球员
            case 3:lable = "球员";break;
            // 国家
            case 4:lable = "国家";break;
            // 其他
            case 5:lable = "其他";break;
        }
        tvLabel.setText(lable);
        tvContent.setText(finalSearchAllBean.getName());
//        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(finalSearchAllBean.getLogo()),imageView);
        DisplayImageByVolleyUtils.loadImage(imageView,UrlHelper.checkUrl(finalSearchAllBean.getLogo()));
        if(position != 0){
            FinalSearchAllBean preFinalSearchAllBean = mDatas.get(position - 1);
            if(preFinalSearchAllBean.getType() == type){
                ViewUtils.hideLayout(llLable);
            }else{
                ViewUtils.showLayouts(llLable);
            }
        }

    }
}
