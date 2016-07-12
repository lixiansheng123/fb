package com.yuedong.football_mad.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.ViewHolder;
import com.yuedong.football_mad.model.bean.DbLookFriendBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.IdAvatarNameUserLeveLastPublishTimelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/12
 */
public class HotFriendAdapter extends BaseAdapter<IdAvatarNameUserLeveLastPublishTimelBean> {
    private List<DbLookFriendBean> lookFriendBeans;
    private User loginUser;
    public void setLookFriendBeans(List<DbLookFriendBean> lookFriendBeans){
        this.lookFriendBeans = lookFriendBeans;
    }
    public HotFriendAdapter(Context con, List<IdAvatarNameUserLeveLastPublishTimelBean> data) {
        super(con, data, R.layout.item_my_ball_friend);
        loginUser = MyApplication.getInstance().getLoginUser();
    }

    @Override
    public void convert(ViewHolder viewHolder, IdAvatarNameUserLeveLastPublishTimelBean idAvatarNameUserLeveLastPublishTimelBean, int position, View convertView) {
        View view = viewHolder.getIdByView(R.id.ll_tag);
        View rlHead = viewHolder.getIdByView(R.id.rl_head);
        RoundImageView ivHead = viewHolder.getIdByView(R.id.iv_user_head);
        TextView tvLevel = viewHolder.getIdByView(R.id.tv_level);
        ImageView icon  = viewHolder.getIdByView(R.id.iv_icon);
        viewHolder.setText(R.id.tv_name, idAvatarNameUserLeveLastPublishTimelBean.getName());
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(idAvatarNameUserLeveLastPublishTimelBean.getUserlevel());
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        tvLevel.setText(TextUtils.addTextColor(userLevelDisplayInfo.textDesc,0,2,userLevelDisplayInfo.partTextColor));
        DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(idAvatarNameUserLeveLastPublishTimelBean.getAvatar()),ivHead);
        if (position == 0)
            ViewUtils.showLayout(view);
        else
            ViewUtils.hideLayout(view);
        // 红点处理
        boolean light = DataUtils.redPointLignt(idAvatarNameUserLeveLastPublishTimelBean.getId(),loginUser.getId(),idAvatarNameUserLeveLastPublishTimelBean.getLastpublishtime(),lookFriendBeans);
        if(light)ViewUtils.showLayout(icon);else ViewUtils.hideLayout(icon);
    }
}
