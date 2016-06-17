package com.yuedong.football_mad.ui;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.UserArticleListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.AbstractPagerAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.DaySelectDialog;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.football_mad.view.SelectBallPosDialog;
import com.yuedong.football_mad.view.SelectSexDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_sel_zan)
    private ImageView ivZan;
    @ViewInject(R.id.iv_sel_new)
    private ImageView ivNew;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    private TextView etSex, etCity, etShangechange, etBirthday, etBindMobile, etJobType, etRealname, etJobCity, etJigou, etContact;
    private List<View> views = new ArrayList<View>();
    private PulltoRefreshListView listView;
    private UserArticleListAdapter adapter;
    private SelectSexDialog selectSexDialog;
    private SelectBallPosDialog selectBallPosDialog;
    private DaySelectDialog daySelectDialog;
    private View view1, view2;
    private boolean other = false;// 用于区分是否是查看自己
    private String userSid;
    private User user;
    private String updateTask;
    private String userInfoTsak;

    //--------------------------post参数----------------------
    private int mSex = -1;
    private int mBallPos = -1;
    private String birthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 控制头部
        int rightIcon = -1;
        View.OnClickListener rightClickListener = null;
        if(!other){
            rightIcon = R.drawable.ic_green_finish;
            rightClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserInfo();
                }
            };
        }else{
            rightIcon = R.drawable.ic_white_add_friend;
        }
        buildUi(new TitleViewHelper(this).getNomarlCenterTitle(R.drawable.ic_round_return, "作者用户名",rightIcon, null, rightClickListener),
                R.layout.activity_user_info);
        other = getIntent().getExtras().getBoolean(Constant.KEY_BOOL, false);
        userSid = getIntent().getExtras().getString(Constant.KEY_STR);
        selectSexDialog = new SelectSexDialog(this);
        selectBallPosDialog = new SelectBallPosDialog(this);
        daySelectDialog = new DaySelectDialog(this);
        daySelectDialog.setCallback(new CommonCallback(){
            @Override
            public void callbackYMR(String year, String month, String day) {
                birthday = year+'-'+month+"-"+day;
                L.d(birthday);
                text(etBirthday,birthday);
            }
        });
        selectSexDialog.setCallback(new CommonCallback() {
            @Override
            public void callback(int id, String text) {
                mSex = id;
                text(etSex,text);
            }
        });
        selectBallPosDialog.setCallback(new CommonCallback(){
            @Override
            public void callback(int id, String text) {
                mBallPos = id;
                text(etShangechange,text);
            }
        });
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {
        Map<String,String> post = new HashMap<String,String>();
        if(mSex != -1)
             post.put("gender",mSex+"");
        if(mBallPos != -1)
            post.put("pos",mBallPos+"");
        if(!TextUtils.isEmpty(birthday))
            post.put("birthday",birthday);
        if(!post.isEmpty()){
            post.put("sid",userSid);
            updateTask=  RequestHelper.post(Constant.URL_ADD_DETAIL,post,BaseResponse.class,false,this);
        }
    }

    @Override
    protected void ui() {
        view1 = getLayoutInflater().inflate(R.layout.layout_common_list, null);
        view2 = getLayoutInflater().inflate(R.layout.layout_user_more_info, null);
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        adapter = new UserArticleListAdapter(this, data);
        listView = (PulltoRefreshListView) view1.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        views.add(view1);
        views.add(view2);
        viewPager.setAdapter(new AbstractPagerAdapter(2) {
            @Override
            public Object getView(ViewGroup container, int position) {
                return views.get(position);
            }
        });
        viewPager.setCurrentItem(0);
        // 获取用户信息
        userInfoTsak = CommonHelper.getUserInfo(userSid,this);

    }

    private void item2Ui() {
        etSex = (TextView) view2.findViewById(R.id.et_sex);
        etCity = (TextView) view2.findViewById(R.id.et_city);
        etShangechange = (TextView) view2.findViewById(R.id.et_shangchang);
        etBirthday = (TextView) view2.findViewById(R.id.et_birthday);
        etJobType = (TextView) view2.findViewById(R.id.et_job_type);
        etRealname = (TextView) view2.findViewById(R.id.et_realname);
        etBindMobile = (TextView) view2.findViewById(R.id.et_mobile);
        etJobCity = (TextView) view2.findViewById(R.id.et_job_city);
        etJigou = (TextView) view2.findViewById(R.id.et_jigou);
        etContact = (TextView) view2.findViewById(R.id.et_contact_info);
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
        if (other) {
            ViewUtils.hideLayout(view2.findViewById(R.id.ll_bangshouji));
            etSex.setClickable(false);
            etCity.setClickable(false);
            etShangechange.setClickable(false);
            etBirthday.setClickable(false);
            etBindMobile.setClickable(false);
            etJobType.setClickable(false);
            etRealname.setClickable(false);
            etBindMobile.setClickable(false);
            etJobCity.setClickable(false);
            etJigou.setClickable(false);
            etContact.setClickable(false);
        }
        String gender =  user.getGender();
        //TODO 填充textview未完成不知道数据结构
        text(etSex, CommonHelper.getTextBySex(Integer.parseInt(gender)));
        text(etBirthday,user.getBirthday());

    }

    @OnClick({R.id.iv_sel_new, R.id.iv_sel_zan, R.id.tv_more})
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
        }
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(!tag.equals(userInfoTsak))T.showShort(this,R.string.str_modify_succeed);
        if(tag.equals(updateTask)){
        }else if(tag.equals(userInfoTsak)){
            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
            this.user = bean.getData().getList();
            item2Ui();
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
            case R.id.et_job_type:
                break;
            case R.id.et_realname:
                break;
            case R.id.et_mobile:
                break;
            case R.id.et_job_city:
                break;
            case R.id.et_jigou:
                break;
            case R.id.et_contact_info:
                break;
        }
    }

    /**
     * 设置文本
     * @param textView
     * @param text
     */
    private void text(TextView textView,String text){
        if(!TextUtils.isEmpty(text)){
            textView.setBackgroundResource(android.R.color.transparent);
            textView.setText(text);
        }else{
            // 如果是看别人的详情
            if(other){
                textView.setBackgroundResource(android.R.color.transparent);
                textView.setText("");
            }
        }
    }
}
