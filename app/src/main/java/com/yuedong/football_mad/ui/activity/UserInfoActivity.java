package com.yuedong.football_mad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.adapter.UserArticleListAdapter;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.AbstractPagerAdapter;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.LocalPhotoActivity;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.TouchBannerListBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.DaySelectDialog;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.football_mad.view.SelectBallPosDialog;
import com.yuedong.football_mad.view.SelectPhotoDialog;
import com.yuedong.football_mad.view.SelectSexDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.Base64;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.FileUtils;
import com.yuedong.lib_develop.utils.ImageZoomUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends LocalPhotoActivity implements View.OnClickListener, SelectPhotoDialog.OnSelectPicPopCallback {
    @ViewInject(R.id.iv_sel_zan)
    private ImageView ivZan;
    @ViewInject(R.id.iv_sel_new)
    private ImageView ivNew;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.rl_country)
    private View rlCountry;
    @ViewInject(R.id.rl_team)
    private View rlTeam;
    @ViewInject(R.id.iv_country)
    private NetworkImageView ivCountry;
    @ViewInject(R.id.iv_team)
    private NetworkImageView ivTeam;
    @ViewInject(R.id.ll_country)
    private View llCountry;
    @ViewInject(R.id.ll_team)
    private View llTeam;
    @ViewInject(R.id.rl_head)
    private View rlHead;
    @ViewInject(R.id.iv_user_head)
    private RoundImageView ivHead;
    @ViewInject(R.id.tv_level)
    private TextView tvLevel;
    @ViewInject(R.id.et_say)
    private EditText etSay;
    private TextView tvNumJiandi, tvNumPost, tvNumComment, tvNumGenTie, tvZanArticle, tvZanComment, tvFriendNum, tvCollectNum, tvAttentionNum;
    private TextView etSex, etCity, etShangechange, etBirthday, etBindMobile, etJobType, etRealname, etJobCity, etJigou, etContact;
    private List<View> views = new ArrayList<View>();
    private PulltoRefreshListView newListView;
    private PulltoRefreshListView zanListView;
    private RefreshProxy<TouchBean> newRefreshProxy = new RefreshProxy<>();
    private RefreshProxy<TouchBean> zanRefreshProxy = new RefreshProxy<>();
    private UserArticleListAdapter newAdapter;
    private UserArticleListAdapter zanAdapter;
    private SelectSexDialog selectSexDialog;
    private SelectBallPosDialog selectBallPosDialog;
    private DaySelectDialog daySelectDialog;
    private View view1,view2, view3;
    private boolean other = false;// 用于区分是否是查看自己
    private String userSid; // 自己资料使用sid
    private String userId; // 别人资料使用userid
    private User user;
    private String updateTask;
    private String userInfoTsak;
    private int qualifystate;
    private boolean isEdit;// 编辑状态
    private ImageView title1Right;
    private SelectPhotoDialog selectPhotoDialog;
    private int dp65;
    private String headTask;

    //--------------------------post参数----------------------
    private int mSex = -1;
    private int mBallPos = -1;
    private String birthday;
    private String countryId;
    private String teamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        // 控制头部
        int rightIcon = -1;
        View.OnClickListener rightClickListener = null;
        if (!other) {
            rightIcon = R.drawable.ic_info_edit;
            rightClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int flag = (int) title1Right.getTag();
                    if (flag == 0) {
                        clickable(true);
                        isEdit = true;
                        title1Right.setTag(1);
                        title1Right.setImageResource(R.drawable.ic_green_finish);
                        if (user != null) {
                            renderUserInfo();
                        }
                    } else {
                        updateUserInfo();
                    }

                }
            };
        } else {
            rightIcon = R.drawable.ic_white_add_friend;
        }
        buildUi(titleViewHelper.getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "作者用户名", rightIcon, null, rightClickListener),
                R.layout.activity_user_info);
        title1Right = titleViewHelper.getTitle1Right();
        title1Right.setTag(0);
        Bundle extras = getIntent().getExtras();
        other = extras.getBoolean(Constant.KEY_BOOL, false);
        userSid = extras.getString(Constant.KEY_STR);
        userId = extras.getString(Constant.KEY_STR2);
        selectSexDialog = new SelectSexDialog(this);
        selectBallPosDialog = new SelectBallPosDialog(this);
        daySelectDialog = new DaySelectDialog(this);
        selectPhotoDialog = new SelectPhotoDialog(this);
        selectPhotoDialog.setOnSelectPicPopCallback(this);
        daySelectDialog.setCallback(new CommonCallback() {
            @Override
            public void callbackYMR(String year, String month, String day) {
                birthday = year + '-' + month + "-" + day;
                L.d(birthday);
                text(etBirthday, birthday);
            }
        });
        selectSexDialog.setCallback(new CommonCallback() {
            @Override
            public void callback(int id, String text) {
                mSex = id;
                text(etSex, text);
            }
        });
        selectBallPosDialog.setCallback(new CommonCallback() {
            @Override
            public void callback(int id, String text) {
                mBallPos = id;
                text(etShangechange, text);
            }
        });
        dp65 = DimenUtils.dip2px(this, 65);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {
        Map<String, String> post = new HashMap<String, String>();
        if (mSex != -1)
            post.put("gender", mSex + "");
        if (mBallPos != -1)
            post.put("pos", mBallPos + "");
        if (!TextUtils.isEmpty(birthday))
            post.put("birthday", birthday);
        if (!TextUtils.isEmpty(countryId)) {
            post.put("favoritecountryid", countryId);
        }
        String sayStr = etSay.getText().toString();
        if (!user.getRemark().equals(sayStr)) {
            post.put("remark", sayStr);
        }
        if (!TextUtils.isEmpty(teamId)) {
            post.put("favoriteteamid", teamId);
        }
        if (!post.isEmpty()) {
            post.put("sid", userSid);
            updateTask = RequestHelper.post(Constant.URL_ADD_DETAIL, post, BaseResponse.class, false, false, this);
        } else {
            T.showShort(activity, "您还没有编辑信息");
        }
    }

    @Override
    protected void ui() {
        tvNumJiandi = (TextView) findViewById(R.id.tv_num_jiandi);
        tvNumPost = (TextView) findViewById(R.id.tv_num_post);
        tvNumComment = (TextView) findViewById(R.id.tv_num_comment);
        tvNumGenTie = (TextView) findViewById(R.id.tv_num_gentie);
        tvZanArticle = (TextView) findViewById(R.id.tv_num_zan_article);
        tvZanComment = (TextView) findViewById(R.id.tv_num_zan_comment);
        view1 = getLayoutInflater().inflate(R.layout.layout_common_list, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_common_list,null);
        view3 = getLayoutInflater().inflate(R.layout.layout_user_more_info, null);
        newListView = (PulltoRefreshListView) view1.findViewById(R.id.listview);
        zanListView = (PulltoRefreshListView) view2.findViewById(R.id.listview);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new AbstractPagerAdapter(2) {
            @Override
            public Object getView(ViewGroup container, int position) {
                return views.get(position);
            }
        });
        if(!other)
             viewPager.setCurrentItem(2);
        else
            viewPager.setCurrentItem(1);
        item2Ui();
        getUserInfo();
    }

    /**
     * 获取用户新闻列表
     */
    private void getUserNews(final String userId) {
        zanRefreshProxy.setPulltoRefreshRefreshProxy(zanListView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new TouchAdapter(activity,data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String, String> post = DataUtils.getListPostMapHasUserId(page + "", count + "", max + "", userId);
                post.put("order","1");
                RequestHelper.post(Constant.URL_USER_NEWS, post, TouchBannerListBean.class, false, false, listener);
            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
        newRefreshProxy.setPulltoRefreshRefreshProxy(newListView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new TouchAdapter(activity, data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String, String> post = DataUtils.getListPostMapHasUserId(page + "", count + "", max + "", userId);
                post.put("order","0");
                RequestHelper.post(Constant.URL_USER_NEWS, post, TouchBannerListBean.class, false, false, listener);
            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });

//        newsListByTimeTask =
    }


    // 获取用户信息
    private void getUserInfo() {
        userInfoTsak = CommonHelper.getUserInfo(userSid, this);
    }

    private void item2Ui() {
        etSex = (TextView) view3.findViewById(R.id.et_sex);
        etCity = (TextView) view3.findViewById(R.id.et_city);
        etShangechange = (TextView) view3.findViewById(R.id.et_shangchang);
        etBirthday = (TextView) view3.findViewById(R.id.et_birthday);
        etJobType = (TextView) view3.findViewById(R.id.et_job_type);
        etRealname = (TextView) view3.findViewById(R.id.et_realname);
        etBindMobile = (TextView) view3.findViewById(R.id.et_mobile);
        etJobCity = (TextView) view3.findViewById(R.id.et_job_city);
        etJigou = (TextView) view3.findViewById(R.id.et_jigou);
        etContact = (TextView) view3.findViewById(R.id.et_contact_info);
        tvFriendNum = (TextView) view3.findViewById(R.id.tv_friend_num);
        tvCollectNum = (TextView) view3.findViewById(R.id.tv_collect_num);
        tvAttentionNum = (TextView) view3.findViewById(R.id.tv_attention_num);
        etSex.setOnClickListener(this);
        etCity.setOnClickListener(this);
        etShangechange.setOnClickListener(this);
        etBirthday.setOnClickListener(this);
        etJobType.setOnClickListener(this);
        etRealname.setOnClickListener(this);
        etBindMobile.setOnClickListener(this);
        etJobCity.setOnClickListener(this);
        etJigou.setOnClickListener(this);
        etContact.setOnClickListener(this);
        clickable(false);
        if (other) {
            ViewUtils.hideLayout(view3.findViewById(R.id.ll_bangshouji));
        }
    }

    private void clickable(boolean click) {
        if (other) click = false;
        etSex.setClickable(click);
        etCity.setClickable(click);
        etShangechange.setClickable(click);
        etBirthday.setClickable(click);
        etBindMobile.setClickable(click);
        etJobType.setClickable(click);
        etRealname.setClickable(click);
        etBindMobile.setClickable(click);
        etJobCity.setClickable(click);
        etJigou.setClickable(click);
        etContact.setClickable(click);
        rlCountry.setClickable(click);
        rlTeam.setClickable(click);
        rlHead.setClickable(click);
        etSay.setEnabled(click);
    }


    private void renderUserInfo() {
        tvFriendNum.setText(user.getFriendcount() + "/" + user.getFanscount());
        tvCollectNum.setText(user.getCollectcount());
        tvAttentionNum.setText(user.getInterestcount());
        // topui
        tvNumJiandi.setText(user.getNewscount());
        tvNumPost.setText(user.getPostcount());
        tvNumComment.setText(user.getCommentcount());
        tvNumGenTie.setText(user.getTotalcomment());
        tvZanArticle.setText(user.getNewsgood());
        tvZanComment.setText(user.getCommentgood());
        String countryLogo = user.getCountrylogo();
        String teamLogo = user.getTeamlogo();
        if (!TextUtils.isEmpty(countryLogo)) {
            loadCountry(countryLogo);
        }
        if (!TextUtils.isEmpty(teamLogo)) {
            loadTeam(teamLogo);
        }
        int userlevel = user.getUserlevel();
        DisplayUserLevelBean userLevelDisplayInfo = CommonHelper.getUserLevelDisplayInfo(userlevel);
        rlHead.setBackgroundResource(userLevelDisplayInfo.headBg);
        tvLevel.setText(com.yuedong.lib_develop.utils.TextUtils.addTextColor(userLevelDisplayInfo.textDesc, 0, 2, userLevelDisplayInfo.partTextColor));
        String headUrl = user.getAvatar();
        ivHead.setImageResource(R.drawable.bg_white);
        if (!TextUtils.isEmpty(headUrl)) {
            DisplayImageByVolleyUtils.loadUserPic(UrlHelper.checkUrl(headUrl), ivHead);
        }
        etSay.setText(user.getRemark());
        // topend
        qualifystate = user.getQualifystate();
        String gender = user.getGender();
        text(etSex, CommonHelper.getTextBySex(Integer.parseInt(gender)));
        text(etBirthday, user.getBirthday());
        text(etCity, user.getCity());
        text(etShangechange, CommonHelper.getTextByBallPos(user.getPos()));
        text(etBindMobile, user.getPhone());
        text(etJobType, CommonHelper.getWorkType(user.getWorktype()));
        text(etRealname, user.getRealname());
        text(etJobCity, user.getWorkcity());
        text(etJigou, user.getOrganization());
        text(etContact, user.getContact());
        //TODO 处理是否看过对方资料
//        User loginUser = MyApplication.getInstance().getLoginUser();
//        if(loginUser!=null){
//
//        }
        getUserNews(user.getId());
    }

    @OnClick({R.id.iv_sel_new, R.id.iv_sel_zan, R.id.tv_more, R.id.rl_country, R.id.rl_team, R.id.rl_head})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_sel_new:
                ivNew.setSelected(true);
                ivZan.setSelected(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.iv_sel_zan:
                ivNew.setSelected(false);
                ivZan.setSelected(true);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_more:
                viewPager.setCurrentItem(1);
                break;

            case R.id.rl_country:
            case R.id.rl_team:
                Intent data = new Intent(activity, ChooseDataKuListActivity.class);
                int action = ChooseDataKuListActivity.ACTION_TEAM;
                if (view.getId() == R.id.rl_country) {
                    action = ChooseDataKuListActivity.ACTION_COUNTRY;
                }
                data.putExtra(Constant.KEY_ACTION, action);
                LaunchWithExitUtils.startActivityForResult(activity, data, 200);
                break;

            case R.id.rl_head:
                selectPhotoDialog.show();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChooseDataKuListActivity.ACTION_COUNTRY || resultCode == ChooseDataKuListActivity.ACTION_TEAM) {
            if (data == null) return;
            String id = data.getStringExtra(Constant.KEY_ID);
            String logo = data.getStringExtra(Constant.KEY_STR);
            if (resultCode == ChooseDataKuListActivity.ACTION_COUNTRY) {
                loadCountry(logo);
                countryId = id;
            } else if (resultCode == ChooseDataKuListActivity.ACTION_TEAM) {
                loadTeam(logo);
                teamId = id;
            }
        } else if (resultCode == AttestationActivity.ACTION_USERINFO) {
            getUserInfo();
        }
    }

    private void loadCountry(String logo) {
        ViewUtils.hideLayout(llCountry);
        ViewUtils.showLayout(ivCountry);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(logo), ivCountry);
    }

    private void loadTeam(String logo) {
        ViewUtils.hideLayout(llTeam);
        ViewUtils.showLayout(ivTeam);
        DisplayImageByVolleyUtils.loadQuadratePic(UrlHelper.checkUrl(logo), ivTeam);
    }

    private boolean updateInfo;

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (!tag.equals(userInfoTsak)) T.showShort(this, R.string.str_modify_succeed);
        if (tag.equals(updateTask)) {
//            updateInfo = true;
            getUserInfo();
        } else if (tag.equals(userInfoTsak)) {
            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
            this.user = bean.getData().getList();
            renderUserInfo();
            if (updateInfo) {
                // 更新用户信息
                MyApplication.getInstance().setLoginuser(this.user);
                MyApplication.getInstance().userInfoChange = true;
                updateInfo = false;
            }
        } else if (tag.equals(headTask)) {
            updateInfo = true;
            getUserInfo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_sex:
                selectSexDialog.show();
                break;
            case R.id.et_city:

                break;
            case R.id.et_shangchang:
                selectBallPosDialog.show();
                break;
            case R.id.et_birthday:
                daySelectDialog.show();
                break;
            case R.id.et_mobile:
                Bundle data = new Bundle();
                data.putInt(Constant.KEY_ACTION, MobileTestActivity.ACTION_MODIFY_MOBILE);
                LaunchWithExitUtils.startActivity(activity, MobileTestActivity.class, data);
                break;

            case R.id.et_job_type:
            case R.id.et_realname:
            case R.id.et_job_city:
            case R.id.et_jigou:
            case R.id.et_contact_info:
                if (qualifystate != 3) {// 认证通过不过去那个页面
                    Intent it = new Intent(activity, AttestationActivity.class);
                    it.putExtra(Constant.KEY_ACTION, AttestationActivity.ACTION_USERINFO);
                    it.putExtra(Constant.KEY_INT, qualifystate);
                    LaunchWithExitUtils.startActivityForResult(activity, it, 200);
                }
                break;
        }
    }

    /**
     * 设置文本
     *
     * @param textView
     * @param text
     */
    private void text(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setBackgroundResource(android.R.color.transparent);
            textView.setText(text);
        } else {
            // 如果是看别人的详情
            if (other || !isEdit) {
//                textView.setBackgroundResource(android.R.color.transparent);
//                textView.setText("");
                ViewUtils.hideLayout(textView);
            }
            if (isEdit) ViewUtils.showLayout(textView);
        }
    }

    @Override
    public void onTakePic() {
        getPhotoByTake(true);
    }

    @Override
    public void onGetPic() {
        getPhotoByPhotoSet(true);
    }


    @Override
    protected void getPicSucceed(String path) {
        String s = ImageZoomUtils.compressImageToFile(path, Config.DIR_PHOTO_UPLOAD, 150);
        String picInfo = Base64.encode(FileUtils.getFileContent(s));
        Map<String, String> post = DataUtils.getSidPostMap(userSid);
        post.put("avatar", picInfo);
        post.put("encode", "jpg");
        headTask = RequestHelper.post(Constant.URL_USER_UPLOAD_HEAD, post, BaseResponse.class, false, false, this);
    }
}
