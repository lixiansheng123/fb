package com.yuedong.football_mad.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.LoginResBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.SignUtils;
import com.yuedong.lib_develop.utils.T;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private String loginTask;
    private String userInfoTask;
    private String cangePasswordTask;
    @ViewInject(R.id.et_username)
    private EditText etUsername;
    @ViewInject(R.id.et_password)
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterIcon(R.drawable.ic_round_return, R.drawable.ic_title_bar_logo, R.drawable.ic_white_register, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchWithExitUtils.startActivity(LoginActivity.this,MobileTestActivity.class);
            }
        }), R.layout.activity_login);
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(loginTask)) {
            LoginResBean bean = (LoginResBean) data;
            String sid = bean.getData().getSid();
            userInfoTask = CommonHelper.getUserInfo(sid,this);
        } else if (tag.equals(cangePasswordTask)) {

        } else if (tag.equals(userInfoTask)) {
            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
            MyApplication.getInstance().setLoginuser(bean.getData().getList());
            MyApplication.getInstance().userInfoChange = true;
            back();
        }
    }

    @OnClick({R.id.btn_login})
    public void btnClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_info:
//                // 完善信息
//                Map<String, String> post = new HashMap<String, String>();
//                post.put("gender", "1");
//                RequestHelper.post(Constant.URL_MODIFY_ADD_DETAIL, post, null, false, this);
//                break;
//            case R.id.btn_modify:
//                //            // 修改密码
//                Map<String, String> post2 = new HashMap<String, String>();
//                post2.put("old", SignUtils.md5("123456"));
//                post2.put("new", SignUtils.md5("000000"));
//                cangePasswordTask = RequestHelper.post(Constant.URL_MODIFY_PASSWORD, post2, null, false, this);
//                break;
            case R.id.btn_login:
                String inpUsername = etUsername.getText().toString();
                String inpPassword = etPassword.getText().toString();
                if(TextUtils.isEmpty(inpUsername)){
                    T.showShort(LoginActivity.this,"用户名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(inpPassword)){
                    T.showShort(LoginActivity.this,"密码不能为空");
                    return;
                }
               //  登录
               Map<String, String> data = new HashMap<String, String>();
              data.put("name", inpUsername);
              data.put("password", SignUtils.md5(inpPassword));
              loginTask = RequestHelper.post(Constant.URL_LONGIN, data, LoginResBean.class, false, this);
                break;
        }
    }
}
