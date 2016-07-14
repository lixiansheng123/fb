package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.AttentionStarBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class MyAttentionStarAdapter extends BaseAdapter<AttentionStarBean> {
    public boolean isEdit;
    private View.OnClickListener onClickListener;
    public void setOnDeleteClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public MyAttentionStarAdapter(Context con, List<AttentionStarBean> data) {
        super(con, data, R.layout.item_my_attention);
    }

    public void deleteItem(String id){
        for(AttentionStarBean b : mDatas){
            if(b.getId().equals(id) && "".equals(b.getInterest())){
                mDatas.remove(b);
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void convert(ViewHolder viewHolder, final AttentionStarBean bean, int position, View convertView) {
        TextView tvLabel = viewHolder.getIdByView(R.id.tv_label);
        final ImageView ivAttention = viewHolder.getIdByView(R.id.iv_attention);
        ImageView ivDelete = viewHolder.getIdByView(R.id.iv_delete) ;
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_head);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAvatar()),ivHead);
        viewHolder.setText(R.id.tv_name,bean.getName());
        View llLabel = viewHolder.getIdByView(R.id.ll_label);
        final String interest = bean.getInterest();
        ViewUtils.hideLayout(ivDelete);
        // label控制....
        if(TextUtils.isEmpty(interest)){
            tvLabel.setText("关注");
            ViewUtils.invisibleLayout(ivAttention);
            ivAttention.setOnClickListener(null);
            if(isEdit){
                ViewUtils.showLayout(ivDelete);
                ivDelete.setTag(bean);
                ivDelete.setOnClickListener(onClickListener);
            }else{
                ViewUtils.hideLayout(ivDelete);
                ivDelete.setOnClickListener(null);
            }
        }
        else {
            tvLabel.setText("热门");
            ViewUtils.showLayout(ivAttention);
            if("0".equals(interest)){
                ivAttention.setImageResource(R.drawable.ic_attention_unselect);

            }else{
                ivAttention.setImageResource(R.drawable.ic_attention_select);
            }
            ivAttention.setTag(bean);
            ivAttention.setOnClickListener(onClickListener);
            /*
            *
            *
            * new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isAttention = false;
                    if("0".equals(bean.getInterest())) isAttention = true;
                    User loginUser = MyApplication.getInstance().getLoginUser();
                    final boolean finalIsAttention = isAttention;
                    DataUtils.attentionBallStar(mCon, loginUser.getSid(), bean.getId(), new VolleyNetWorkCallback() {
                        @Override
                        public void onNetworkStart(String tag) {

                        }

                        @Override
                        public void onNetworkSucceed(String tag, BaseResponse data) {
                            if(finalIsAttention) {
                                bean.setInterest("1");
                                ivAttention.setImageResource(R.drawable.ic_attention_select);
                            } else {
                                bean.setInterest("0");
                                ivAttention.setImageResource(R.drawable.ic_attention_unselect);
                            }
                        }

                        @Override
                        public void onNetworkError(String tag, VolleyError error) {

                        }
                    },isAttention);
                }
            }
            *
            * */
        }
        if(position != 0){
            AttentionStarBean preBean = mDatas.get(position - 1);
            if(preBean.getInterest().equals(bean.getInterest())){
                ViewUtils.hideLayout(llLabel);
            }else{
                ViewUtils.showLayout(llLabel);
            }
        }

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString(Constant.KEY_STR2, bean.getId());
//                b.putString(Constant.KEY_STR, bean.getUsername());
                LaunchWithExitUtils.startActivity((Activity) mCon, UserInfoActivity.class, b);
            }
        });
    }
}
