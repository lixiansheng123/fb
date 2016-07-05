package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class RegisterActivity extends BaseActivity {
    @ViewInject(R.id.et_username)
    private EditText etUsername;
    @ViewInject(R.id.et_password)
    private EditText etPassword;
    @ViewInject(R.id.et_password_confirm)
    private EditText etConfirmPassword;
    private String registerTask;
    private String userInfoTask;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NoRight(R.drawable.ic_round_return, R.drawable.ic_title_bar_logo, null),
                R.layout.activity_register);
        mobile=   getIntent().getExtras().getString(Constant.KEY_STR);
        autoLoadView = false;
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(registerTask)) {
            LoginResBean bean = (LoginResBean) data;
            userInfoTask = CommonHelper.getUserInfo(bean.getData().getSid(), this);
        } else if (tag.equals(userInfoTask)) {
            showLoadView(false);
            GetUserInfoByIdResBean bean = (GetUserInfoByIdResBean) data;
            MyApplication.getInstance().setLoginuser(bean.getData().getList());
            MyApplication.getInstance().userInfoChange = true;
            LaunchWithExitUtils.startActivity(activity, HomeActivity.class);
            back();
        }
    }

    @OnClick({R.id.btn_go})
    protected void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                String userName = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    T.showShort(activity, "用户名不可为空");
                    return;
                }
                if(userName.length() < 4){
                    T.showShort(activity,"用户名不得少于4个字符");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    T.showShort(activity, "密码不可为空");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    T.showShort(activity, "确认密码不可为空");
                    return;
                }
                if (!password.equals(password2)) {
                    T.showShort(activity, "两次密码输入不一致");
                    return;
                }
                showLoadView(true);
                Map<String, String> post = new HashMap<>();
                post.put("name", userName);
                post.put("phone",mobile);
                post.put("password", SignUtils.md5(password));
                registerTask = RequestHelper.post(Constant.URL_REGISTER, post, LoginResBean.class, false,false, this);
                break;
        }
    }

}
