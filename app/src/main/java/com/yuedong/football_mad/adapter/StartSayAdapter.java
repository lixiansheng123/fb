package com.yuedong.football_mad.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.StarSayBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class StartSayAdapter extends BaseAdapter<StarSayBean> {
    public StartSayAdapter(Context con, List<StarSayBean> data) {
        super(con, data, R.layout.item_home_star_say);
    }

    @Override
    public void convert(ViewHolder viewHolder, final StarSayBean bean, int position, View convertView) {
        NetworkImageView imageView = viewHolder.getIdByView(R.id.iv_pic);
        NetworkImageView imageView1 = viewHolder.getIdByView(R.id.iv_pic2);
        RoundImageView imageView2 = viewHolder.getIdByView(R.id.iv_head);
        final ImageView imageView3 = viewHolder.getIdByView(R.id.iv_collect);
        viewHolder.setText(R.id.tv_name, bean.getUsername())
                .setText(R.id.tv_comment_num, bean.getComment())
                .setText(R.id.tv_content, bean.getDigest())
                .setText(R.id.tv_time, DateUtils.getDescriptionTimeFromTimestamp(bean.getCreatetime() * 1000));
        DisplayImageByVolleyUtils.loadImage(imageView, UrlHelper.checkUrl(bean.getSnapshot()));
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(bean.getThumbnail()), imageView1);
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(bean.getAvatar()), imageView2);
        if (bean.getInterest() == 1) {
            imageView3.setImageResource(R.drawable.ic_xin_select);
        } else {
            imageView3.setImageResource(R.drawable.ic_xin_unselect);

        }
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User loginUser = CommonHelper.checkLogin((Activity) mCon);
                if (loginUser == null) return;
                boolean isAttention = true;
                if (bean.getInterest() == 1) isAttention = false;
                final boolean finalIsAttention = isAttention;
                DataUtils.attentionBallStar(mCon, loginUser.getSid(), bean.getAuthorid(), new VolleyNetWorkCallback() {
                    @Override
                    public void onNetworkStart(String tag) {

                    }

                    @Override
                    public void onNetworkSucceed(String tag, BaseResponse data) {
                        if (finalIsAttention) {
                            bean.setInterest(1);
                            imageView3.setImageResource(R.drawable.ic_xin_select);
                        } else {
                            bean.setInterest(0);
                            imageView3.setImageResource(R.drawable.ic_xin_unselect);
                        }
                    }

                    @Override
                    public void onNetworkError(String tag, VolleyError error) {

                    }
                }, isAttention);

            }
        });
    }
}
