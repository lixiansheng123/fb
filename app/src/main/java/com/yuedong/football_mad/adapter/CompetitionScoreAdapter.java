package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.bean.SimplePicConfig;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/24
 */
public class CompetitionScoreAdapter extends BaseAdapter<List<String>> {
    private SimplePicConfig config;
    public CompetitionScoreAdapter(Context con, List<List<String>> data) {
        super(con, data, R.layout.item_scoreboard);
        config = new SimplePicConfig();
        config.setLoadPic(-1);
        config.setErrorPic(-1);
    }

    @Override
    public void convert(ViewHolder viewHolder, List<String> strings, int position, View convertView) {
        View top = viewHolder.getIdByView(R.id.ll_top);
        NetworkImageView logo = viewHolder.getIdByView(R.id.iv_logo);
        if (position == 0) ViewUtils.showLayout(top);
        else ViewUtils.hideLayout(top);
        if (strings.size() != 10) return;
        DisplayImageByVolleyUtils.loadImage(logo, UrlHelper.checkUrl(strings.get(0)));
        viewHolder.setText(R.id.tv_number, position + 1 + "")
                .setText(R.id.tv_name, strings.get(1))
                .setText(R.id.tv_score1, strings.get(2))
                .setText(R.id.tv_score2, strings.get(3))
                .setText(R.id.tv_score3, strings.get(4))
                .setText(R.id.tv_score4, strings.get(5))
                .setText(R.id.tv_score5, strings.get(6))
                .setText(R.id.tv_score6, strings.get(7))
                .setText(R.id.tv_score7, strings.get(8))
                .setText(R.id.tv_score8, strings.get(9));
    }
}
