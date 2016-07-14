package com.yuedong.football_mad.framework;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.HonorBean;
import com.yuedong.football_mad.model.bean.HonorRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.activity.AcviteActivity;
import com.yuedong.football_mad.ui.activity.AppSettingActivity;
import com.yuedong.football_mad.ui.activity.AttestationActivity;
import com.yuedong.football_mad.ui.activity.BallplayerActivity;
import com.yuedong.football_mad.ui.activity.DataKuActivity;
import com.yuedong.football_mad.ui.activity.LoginActivity;
import com.yuedong.football_mad.ui.activity.MyArticleActivity;
import com.yuedong.football_mad.ui.activity.MyAttentionActivity;
import com.yuedong.football_mad.ui.activity.MyBallFriendActivity;
import com.yuedong.football_mad.ui.activity.MyCollectActivity;
import com.yuedong.football_mad.ui.activity.MyCommentListActivity;
import com.yuedong.football_mad.ui.activity.MyHonorActivity;
import com.yuedong.football_mad.ui.activity.MyMsgActivity;
import com.yuedong.football_mad.ui.activity.UserInfoActivity;
import com.yuedong.football_mad.ui.activity.VideoListActivity;
import com.yuedong.football_mad.ui.activity.WatchBallActivity;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.FileUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.TextUtils;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.io.File;
import java.io.Serializable;
import java.util.List;


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
    private TextView tvUsername, tvUserLevel, tvNumJiandi, tvNumPost, tvNumComment, tvNumGenTie, tvZanArticle, tvZanComment, tvAStatus;
    private User loginUser;
    private String userHonorList;
    private int qualifystate;
    private View llHonor1,llHonor2,llHonor3;
    private ImageView ivHonor1,ivHonor2,ivHonor3;
    private TextView tvHonor1,tvHonor2,tvHonor3;;
    private List<HonorBean> myHonorList;

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
        tvNumJiandi = (TextView) menu.findViewById(R.id.tv_num_jiandi);
        tvNumPost = (TextView) menu.findViewById(R.id.tv_num_post);
        tvNumComment = (TextView) menu.findViewById(R.id.tv_num_comment);
        tvNumGenTie = (TextView) menu.findViewById(R.id.tv_num_gentie);
        tvZanArticle = (TextView) menu.findViewById(R.id.tv_num_zan_article);
        tvZanComment = (TextView) menu.findViewById(R.id.tv_num_zan_comment);
        tvAStatus = (TextView) menu.findViewById(R.id.tv_a_status);
        llHonor1 = menu.findViewById(R.id.ll_honor1);
        llHonor2 = menu.findViewById(R.id.ll_honor2);
        llHonor3 = menu.findViewById(R.id.ll_honor3);
        ivHonor1 = (ImageView) menu.findViewById(R.id.iv_honor1);
        ivHonor2 = (ImageView) menu.findViewById(R.id.iv_honor2);
        ivHonor3 = (ImageView) menu.findViewById(R.id.iv_honor3);
        tvHonor1 = (TextView) menu.findViewById(R.id.tv_honor1);
        tvHonor2 = (TextView) menu.findViewById(R.id.tv_honor2);
        tvHonor3 = (TextView) menu.findViewById(R.id.tv_honor3);
        menu.findViewById(R.id.ll_my_attention).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_comment).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_ball_friend).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_honor).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_collect).setOnClickListener(this);
        menu.findViewById(R.id.ll_my_article).setOnClickListener(this);
        menu.findViewById(R.id.ll_msg).setOnClickListener(this);
        menu.findViewById(R.id.ll_dataku).setOnClickListener(this);
        menu.findViewById(R.id.ll_watch_ball).setOnClickListener(this);
        menu.findViewById(R.id.ll_exit_logo).setOnClickListener(this);
        menu.findViewById(R.id.ll_setting).setOnClickListener(this);
        menu.findViewById(R.id.ll_video_list).setOnClickListener(this);
        menu.findViewById(R.id.ll_ballplayer).setOnClickListener(this);
        menu.findViewById(R.id.ll_active).setOnClickListener(this);
        changeUi();
    }

//    @Override
//    public void networdSucceed(String tag, BaseResponse data) {
//        if(tag.equals(infoTask)){
//            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
//        }
//    }

//    private void getUserInfo() {
//        User user = MyApplication.getInstance().getLoginUser();
//        if(user == null)return;
//        infoTask=  CommonHelper.getUserInfo(user.getSid(),SideActivity.this);
//
//    }

    private void changeUi() {
        loginUser = MyApplication.getInstance().getLoginUser();
//
        if (loginUser == null) return;
        int userLevel = loginUser.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userLevel);
        String head = UrlHelper.checkUrl(loginUser.getAvatar());
        L.d("头像:" + head);
        DisplayImageByVolleyUtils.loadUserPic(head, ivUserHead);
        tvUsername.setText(loginUser.getName());
        SpannableStringBuilder sp = TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor);
        tvUserLevel.setText(sp);
        rlUserHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        tvNumJiandi.setText(loginUser.getNewscount());
        tvNumPost.setText(loginUser.getPostcount());
        tvNumComment.setText(loginUser.getCommentcount());
        tvNumGenTie.setText(loginUser.getTotalcomment());
        tvZanArticle.setText(loginUser.getNewsgood());
        tvZanComment.setText(loginUser.getCommentgood());
        // 荣誉
        getUserHonor();
        // 认证
        qualifystate = loginUser.getQualifystate();
        String msg = "未认证";
        switch (qualifystate) {
            case 0:
                break;

            case 1:
                msg = "认证中";
                break;

            case 2:
                msg = "认证不通过";
                break;

            case 3:
                msg = "认证通过";
                break;
        }
        tvAStatus.setText(msg);
    }

    private void getUserHonor() {
        userHonorList = RequestHelper.post(Constant.URL_USER_HONOR, DataUtils.getSidPostMap(loginUser.getSid()), HonorRespBean.class,true,true,this);
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
                if (CommonHelper.checkLogin(activity) == null) return;
                // 认证通过不进入认证页面
                if (qualifystate != 3) {
                    Intent it = new Intent(activity, AttestationActivity.class);
                    it.putExtra(Constant.KEY_INT, qualifystate);
                    LaunchWithExitUtils.startActivity(activity, it);
                }
                break;
            case R.id.rl_user_info:
                if ( CommonHelper.checkLogin(activity) == null) return;
                Bundle data = new Bundle();
                data.putString(Constant.KEY_STR2, loginUser.getId());
                LaunchWithExitUtils.startActivity(SideActivity.this, UserInfoActivity.class, data);
                break;
            case R.id.ll_my_attention:
                if (CommonHelper.checkLogin(activity) == null) return;
                LaunchWithExitUtils.startActivity(SideActivity.this, MyAttentionActivity.class);
                break;

            case R.id.ll_my_comment:
                if (CommonHelper.checkLogin(activity) == null) return;
                LaunchWithExitUtils.startActivity(SideActivity.this, MyCommentListActivity.class);
                break;
            case R.id.ll_my_ball_friend:
                if (CommonHelper.checkLogin(activity) == null) return;
                LaunchWithExitUtils.startActivity(SideActivity.this, MyBallFriendActivity.class);
                break;

            case R.id.ll_my_honor:
                if (CommonHelper.checkLogin(activity) == null) return;
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_SERIALIZABLE,(Serializable)myHonorList);
                LaunchWithExitUtils.startActivity(SideActivity.this, MyHonorActivity.class,bundle);
                break;

            case R.id.ll_my_collect:
                if (CommonHelper.checkLogin(activity) == null) return;
                LaunchWithExitUtils.startActivity(SideActivity.this, MyCollectActivity.class);
                break;

            case R.id.ll_my_article:
                LaunchWithExitUtils.startActivity(SideActivity.this, MyArticleActivity.class);
                break;

            case R.id.ll_msg:
                if (CommonHelper.checkLogin(activity) == null) return;
                LaunchWithExitUtils.startActivity(SideActivity.this, MyMsgActivity.class);
                break;

            case R.id.ll_dataku:
                LaunchWithExitUtils.startActivity(activity, DataKuActivity.class);
                break;

            case R.id.ll_watch_ball:
                LaunchWithExitUtils.startActivity(activity, WatchBallActivity.class);
                break;

            case R.id.ll_exit_logo:
                MyApplication.getInstance().setLoginuser(null);
                String cacheFile = FileUtils.getDiskCacheDir(App.getInstance().getAppContext()) + "userinfo.c";
                boolean flag = FileUtils.deleteFile(new File(cacheFile));
                Intent it = new Intent(activity, LoginActivity.class);
                it.putExtra(Constant.KEY_BOOL, true);
                LaunchWithExitUtils.startActivity(activity, it);
                back();
                break;

            case R.id.ll_setting:
                LaunchWithExitUtils.startActivity(activity, AppSettingActivity.class);
                break;

            case R.id.ll_video_list:
                LaunchWithExitUtils.startActivity(activity, VideoListActivity.class);
                break;

            case R.id.ll_ballplayer:
                LaunchWithExitUtils.startActivity(activity, BallplayerActivity.class);
                break;

            case R.id.ll_active:
                LaunchWithExitUtils.startActivity(activity,AcviteActivity.class);
                break;
        }
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(userHonorList)){
            HonorRespBean bean = (HonorRespBean) data;
            myHonorList = bean.getData().getList();
            try {
                HonorBean honorBean = myHonorList.get(0);
                ViewUtils.showLayout(llHonor1);
                DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(honorBean.getLogo()), ivHonor1);
                tvHonor1.setText(honorBean.getName());

                HonorBean honorBean1 = myHonorList.get(1);
                ViewUtils.showLayout(llHonor2);
                DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(honorBean1.getLogo()), ivHonor2);
                tvHonor2.setText(honorBean1.getName());

                HonorBean honorBean2 = myHonorList.get(0);
                ViewUtils.showLayout(llHonor3);
                DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(honorBean2.getLogo()), ivHonor3);
                tvHonor3.setText(honorBean2.getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
