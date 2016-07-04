package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DbAttention;
import com.yuedong.football_mad.model.bean.GameListBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.T;
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
    private List<DbAttention> attentionGameList;
    private DbUtils db;


    public GameListAdapter(Context con, List<GameListBean> data) {
        super(con, data, R.layout.item_game_list);

        db = DbUtils.create(con);
        updateDbAttention();
    }

    private void updateDbAttention() {
        attentionGameList = DataUtils.getMyAttentionGameList();
    }

    /**
     * 是否关注了比赛
     */
    private boolean isAttention(String id) {
        if (attentionGameList == null || attentionGameList.isEmpty()) return false;
        for (DbAttention dbAttention : attentionGameList) {
            if (dbAttention.getId().equals(id)) return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder viewHolder, final GameListBean gameListBean, int position, View convertView) {
        TextView tvLableTime = viewHolder.getIdByView(R.id.tv_label_time);
        NetworkImageView homeLogo = viewHolder.getIdByView(R.id.iv_home_logo);
        NetworkImageView questLogo = viewHolder.getIdByView(R.id.iv_guest_logo);
        ImageView ivCollect = viewHolder.getIdByView(R.id.iv_collect);
        TextView tvScore = viewHolder.getIdByView(R.id.tv_score);
        TextView tvTime = viewHolder.getIdByView(R.id.tv_game_time);
        NetworkImageView ivSmallIcon = viewHolder.getIdByView(R.id.iv_logo);
        tvLableTime.setText(gameListBean.getContesttime());
        viewHolder.setText(R.id.tv_channel, "直播:" + gameListBean.getLive())
                .setText(R.id.tv_home_name, gameListBean.getHometeam())
                .setText(R.id.tv_guest_name, gameListBean.getGuestteam());
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(gameListBean.getHometeamlogo()), homeLogo);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(gameListBean.getGuestteamlogo()), questLogo);
        try {
            String week = DateUtils.getWeekByDate(DateUtils.parseString(gameListBean.getContesttime(), new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd)), "周");
            tvTime.setText(week + "-" + gameListBean.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // mark
        String mark = gameListBean.getMake();
        if (!TextUtils.isEmpty(mark)) {
            ViewUtils.showLayout(ivSmallIcon);
            DisplayImageByVolleyUtils.loadImage(ivSmallIcon, UrlHelper.checkUrl(gameListBean.getMake()));
        } else {
            ViewUtils.hideLayout(ivSmallIcon);
        }
        // 判断收藏按钮出现
        String score = gameListBean.getScore();
        tvScore.setText(score);
        if (TextUtils.isEmpty(score)) {
            ViewUtils.showLayout(ivCollect);
            ViewUtils.hideLayout(tvScore);
        } else {
            ViewUtils.showLayout(tvScore);
            ViewUtils.hideLayout(ivCollect);
        }
        if (isAttention(gameListBean.getId())){
            ivCollect.setImageResource(R.drawable.ic_green_big_star_select);
            ivCollect.setTag(true);
            gameListBean.attention = true;
        } else{
            ivCollect.setImageResource(R.drawable.ic_green_big_star_unselect);
            ivCollect.setTag(false);
            gameListBean.attention = false;
        }
        // 收藏按钮点击事件
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(boolean)v.getTag()){
                    if (DataUtils.attentionGame(gameListBean.getId())) {
                        T.showShort(mCon, "关注成功");
                        updateDbAttention();
                        notifyDataSetChanged();
                    }
                }else{
                    DataUtils.cancelAttentionGame(gameListBean.getId());
                    T.showShort(mCon, "取消关注成功");
                    updateDbAttention();
                    notifyDataSetChanged();
                }
            }
        });
        // lable 控制
        ViewUtils.showLayout(tvLableTime);
        if (position != 0) {
            tvLableTime.setBackgroundResource(R.drawable.bg_black666666_round4dp);
            GameListBean preBean = mDatas.get(position - 1);
            if (preBean.getContesttime().equals(gameListBean.getContesttime()))
                ViewUtils.hideLayout(tvLableTime);
        } else {
            tvLableTime.setBackgroundResource(R.drawable.bg_green_round);
        }
    }
}
