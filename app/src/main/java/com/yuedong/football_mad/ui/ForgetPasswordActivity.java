package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.T;

public class ForgetPasswordActivity extends BaseActivity {
    @ViewInject(R.id.et_password)
    private EditText etPassword;
    @ViewInject(R.id.et_password_confirm)
    private EditText etPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NoRight(R.drawable.ic_round_return,R.drawable.ic_title_bar_logo,null),
                R.layout.activity_forget_password);
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.btn_confirm})
    protected void clickEvent(View view){
        switch(view.getId()){
            case R.id.btn_confirm:
                String password = etPassword.getText().toString();
                String password2 = etPasswordConfirm.getText().toString();
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
                break;
        }
    }
}
