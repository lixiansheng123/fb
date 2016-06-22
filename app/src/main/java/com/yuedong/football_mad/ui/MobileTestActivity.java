package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

public class MobileTestActivity extends BaseActivity {
    public static int ACTION_REGISTER = 0x001;// 注册
    public static int ACTION_FORGET_PASSWORD = 0x002;// 忘记密码
    private int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NoRight(R.drawable.ic_round_return, R.drawable.ic_title_bar_logo, null),
                R.layout.activity_mobile_test);
        action = getIntent().getIntExtra(Constant.KEY_ACTION, ACTION_REGISTER);
    }

    @Override
    protected void ui() {

    }


    @OnClick({R.id.btn_confirm})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (action == ACTION_REGISTER)
                    LaunchWithExitUtils.startActivity(activity, RegisterActivity.class);
                else if (action == ACTION_FORGET_PASSWORD)
                    LaunchWithExitUtils.startActivity(activity, ForgetPasswordActivity.class);
                break;
        }
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
