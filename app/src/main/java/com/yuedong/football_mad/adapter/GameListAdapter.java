package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.GameListBean;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 俊鹏 on 2016/7/1
 */
public class GameListAdapter extends BaseAdapter<GameListBean> {
    public GameListAdapter(Context con, List<GameListBean> data) {
        super(con, data, R.layout.item_game_list);
    }

    @Override
    public void convert(ViewHolder viewHolder, GameListBean gameListBean, int position, View convertView) {
        TextView tvLableTime = viewHolder.getIdByView(R.id.tv_label_time);
        tvLableTime.setText(gameListBean.getContesttime());
        NetworkImageView homeLogo = viewHolder.getIdByView(R.id.iv_home_logo);
        NetworkImageView questLogo = viewHolder.getIdByView(R.id.iv_guest_logo);
        ImageView ivCollect = viewHolder.getIdByView(R.id.iv_collect);
        TextView tvScore = viewHolder.getIdByView(R.id.tv_score);
        TextView tvTime = viewHolder.getIdByView(R.id.tv_game_time);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(gameListBean.getHometeamlogo()),homeLogo);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(gameListBean.getGuestteamlogo()),questLogo);
        try {
            String week = DateUtils.getWeekByDate(DateUtils.parseString(gameListBean.getContesttime(), new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd)),"周");
            tvTime.setText(week+"-"+gameListBean.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断收藏按钮出现
        String score = gameListBean.getScore();
        tvScore.setText(score);
        if(TextUtils.isEmpty(score)){
            ViewUtils.showLayout(ivCollect);
            ViewUtils.hideLayout(tvScore);
        }else{
            ViewUtils.showLayout(tvScore);
            ViewUtils.hideLayout(ivCollect);
        }
        // 收藏按钮点击事件
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       // TODO 判断 比赛是否结束
//        String fullTime = gameListBean.getContesttime()+" "+gameListBean.getTime();
//        Date gameDate = DateUtils.parseString(fullTime, new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd_HH_mm));
//        if(System.currentTimeMillis() > gameDate.getTime()){
//            // 已经结束
//            tv
//        }else{
//            // 未结束
//        }

        // lable 控制
        ViewUtils.showLayout(tvLableTime);
        if(position != 0){
            tvLableTime.setBackgroundResource(R.drawable.bg_black666666_round4dp);
            GameListBean preBean = mDatas.get(position - 1);
            if(preBean.getContesttime().equals(gameListBean.getContesttime()))
                ViewUtils.hideLayout(tvLableTime);
        }else{
            tvLableTime.setBackgroundResource(R.drawable.bg_green_round);
        }
    }
}
