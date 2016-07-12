package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.bean.VideoBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.VideoActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/8
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    private List<VideoBean> data;
    private Context context;
    public VideoListAdapter(Context context){
        this.context = context;
    }

    public void setData(List<VideoBean> data){
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoBean bean = data.get(position);
        holder.tvTitle.setText(bean.getName());
        holder.tvContent.setText(bean.getRemark());
        // 播放时长
        long playtime = bean.getPlaytime();
        int minute = (int) Math.floor(playtime/ 60);
        if(minute != 0){
            playtime -= minute*60;
        }
        String minuteStr = minute<10?"0"+minute:minute+"";
        String sencondStr = playtime<10?"0"+playtime:playtime+"";
        holder.tvTime.setText(minuteStr+":"+sencondStr);
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(bean.getThumbnail()), holder.ivPic);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAvatar()),holder.ivHead);
        int userLevel = bean.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userLevel);
        holder.tvName.setText(bean.getUsername());
        holder.rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        holder.llUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.llPlayInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = bean.getPath();
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR,url);
                LaunchWithExitUtils.startActivity((Activity)context, VideoActivity.class,data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvContent;
        TextView tvTitle;
        View rlHead;
        RoundImageView ivHead,ivPic;
        TextView tvTime;
        View llPlayInfo,llUserInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            rlHead = itemView.findViewById(R.id.rl_head);
            ivHead = (RoundImageView) itemView.findViewById(R.id.iv_user_head);
            ivPic = (RoundImageView) itemView.findViewById(R.id.iv_vedio_pic);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            llPlayInfo = itemView.findViewById(R.id.ll_play_info);
            llUserInfo = itemView.findViewById(R.id.ll_user_info);
        }
    }
}
