package com.yuedong.football_mad.ui;

import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.LoginResBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private String loginTask;
    private String userInfoTask;
    private String cangePasswordTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_login);
    }

    @Override
    protected void ui() {
        // 登录
        Map<String, String> data = new HashMap<String, String>();
        data.put("name", "leo");
        data.put("password", SignUtils.md5("123456"));
        loginTask = RequestHelper.post(Constant.URL_LONGIN, data, LoginResBean.class, false, this);
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

    @OnClick({R.id.btn_info, R.id.btn_modify})
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_info:
                // 完善信息
                Map<String, String> post = new HashMap<String, String>();
                post.put("gender", "1");
                RequestHelper.post(Constant.URL_MODIFY_ADD_DETAIL, post, null, false, this);
                break;
            case R.id.btn_modify:
                //            // 修改密码
                Map<String, String> post2 = new HashMap<String, String>();
                post2.put("old", SignUtils.md5("123456"));
                post2.put("new", SignUtils.md5("000000"));
                cangePasswordTask = RequestHelper.post(Constant.URL_MODIFY_PASSWORD, post2, null, false, this);
                break;
        }
    }
}
