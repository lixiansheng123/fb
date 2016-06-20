package com.yuedong.football_mad.framework;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.AttestationActivity;
import com.yuedong.football_mad.ui.LoginActivity;
import com.yuedong.football_mad.ui.MyAttentionActivity;
import com.yuedong.football_mad.ui.MyCommentListActivity;
import com.yuedong.football_mad.ui.UserInfoActivity;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.view.RoundImageView;


/**
 * 侧边栏
 *
 * @author 俊鹏 on 2016/6/6
 */
public abstract class SideActivity extends BaseActivity implements View.OnClickListener {
    private SlidingMenu slidingMenu;
    private View menu;
    private RoundImageView ivUserHead;
    private View rlUserHead;
    private TextView tvUsername, tvUserLevel;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean userInfoChange = MyApplication.getInstance().userInfoChange;
        if (userInfoChange) {
            changeUi();
            MyApplication.getInstance().userInfoChange = false;
        }
    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu = getLayoutInflater().inflate(R.layout.layout_slidingmenu, null);
        slidingMenu.setMenu(menu);
        menu.findViewById(R.id.rl_attest).setOnClickListener(this);
        menu.findViewById(R.id.rl_user_info).setOnClickListener(this);
        ivUserHead = (RoundImageView) menu.findViewById(R.id.iv_user_head);
        tvUsername = (TextView) menu.findViewById(R.id.tv_username);
        tvUserLevel = (TextView) menu.findViewById(R.id.tv_level);
        rlUserHead = menu.findViewById(R.id.rl_head);
        menu.findViewById(R.id.ll_my_attention).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_comment).setOnClickListener(this);
        changeUi();
    }

    private void changeUi() {
        loginUser = MyApplication.getInstance().getLoginUser();
        if (loginUser == null) return;
        int userLevel = Integer.parseInt(loginUser.getUserlevel());
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userLevel);
        DisplayImageByVolleyUtils.loadImage(UrlHelper.checkUrl(loginUser.getAvatar()), ivUserHead);
        tvUsername.setText(loginUser.getName());
        SpannableStringBuilder sp = TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor);
        tvUserLevel.setText(sp);
        rlUserHead.setBackgroundResource(userLevelDisplayInfo.headBg);
    }


    protected void toggle(boolean update) {
        if (update) {
        }
        slidingMenu.toggle(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_attest:
                LaunchWithExitUtils.startActivity(SideActivity.this, AttestationActivity.class);
                break;
            case R.id.rl_user_info:
                if (loginUser == null)
                    LaunchWithExitUtils.startActivity(SideActivity.this, LoginActivity.class);
                else{
                    Bundle data = new Bundle();
                    data.putString(Constant.KEY_STR,loginUser.getSid());
                    LaunchWithExitUtils.startActivity(SideActivity.this, UserInfoActivity.class,data);
                }
                break;
            case R.id.ll_my_attention:
                LaunchWithExitUtils.startActivity(SideActivity.this, MyAttentionActivity.class);
                break;

            case R.id.ll_my_comment:
                LaunchWithExitUtils.startActivity(SideActivity.this, MyCommentListActivity.class);
                break;
        }
    }
}
