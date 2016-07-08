package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MobileTestActivity extends BaseActivity {
    public static int ACTION_REGISTER = 0x001;// 注册
    public static int ACTION_FORGET_PASSWORD = 0x002;// 忘记密码
    public static int ACTION_MODIFY_MOBILE = 0x003;// 换绑手机
    private int action;
    @ViewInject(R.id.et_mobile)
    private EditText etMobile;
    @ViewInject(R.id.et_code)
    private EditText etCode;
    @ViewInject(R.id.tv_second)
    private TextView tvSecond;
    @ViewInject(R.id.btn_verify)
    private Button btnCode;
    private String getCodeTask, testCodeTask, updateInfoTask;
    private Timer timer;
    private TimerTask timerTask;
    private int second = 90;// 倒计时秒数
    private String mobileStr;
    private String codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NoRight(R.drawable.ic_round_return, R.drawable.ic_title_bar_logo, null),
                R.layout.activity_mobile_test);
        action = getIntent().getIntExtra(Constant.KEY_ACTION, ACTION_REGISTER);
    }

    private void startTimer() {
        second = 90;
        tvSecond.setText(second + "s");
        btnCode.setSelected(true);
        btnCode.setClickable(false);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        second--;
                        if (second <= 0) {
                            timer.cancel();
                            second = 90;
                            tvSecond.setText(second + "s");
                            btnCode.setSelected(false);
                            btnCode.setClickable(true);
                            return;
                        }
                        tvSecond.setText(second + "s");
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    protected void ui() {

    }


    @OnClick({R.id.btn_confirm, R.id.btn_verify})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (!check(true)) return;
                Map<String, String> post2 = new HashMap<>();
                post2.put("phone", mobileStr);
                post2.put("code", codeStr);
                testCodeTask = RequestHelper.post(Constant.URL_USER_CHECK_CODE, post2, BaseResponse.class, false, false, MobileTestActivity.this);

                break;

            case R.id.btn_verify:
                if (!check(false)) return;
                String url = Constant.URL_USER_CODE_BY_NEW;
                if (action == ACTION_FORGET_PASSWORD)
                    url = Constant.URL_USER_CODE_BY_OLD;
                Map<String, String> post = new HashMap<>();
                post.put("phone", mobileStr);
                getCodeTask = RequestHelper.post(url, post, BaseResponse.class, false, false, MobileTestActivity.this);
                break;
        }
    }

    private boolean check(boolean confirm) {
        mobileStr = etMobile.getText().toString();
        if (TextUtils.isEmpty(mobileStr)) {
            T.showShort(activity, "请输入手机号码");
            return false;
        }
        if (!mobileStr.matches(Config.MOBILE_RULE)) {
            T.showShort(activity, "请输入正确的手机号码");
            return false;
        }
        if (confirm) {
            codeStr = etCode.getText().toString();
            if (TextUtils.isEmpty(codeStr)) {
                T.showShort(activity, "请输入验证码");
                return false;
            }
        }
        return true;
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(getCodeTask)) {
            startTimer();
        } else if (tag.equals(testCodeTask)) {
            timer.cancel();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_STR, mobileStr);
            if (action == ACTION_REGISTER) {
                LaunchWithExitUtils.startActivity(activity, RegisterActivity.class, bundle);
            } else if (action == ACTION_FORGET_PASSWORD) {
                LaunchWithExitUtils.startActivity(activity, ForgetPasswordActivity.class, bundle);
            } else if (action == ACTION_MODIFY_MOBILE) {
                User loginUser = MyApplication.getInstance().getLoginUser();
                Map<String, String> post = new HashMap<>();
                post.put("phone", mobileStr);
                post.put("sid", loginUser.getSid());
                updateInfoTask = RequestHelper.post(Constant.URL_ADD_DETAIL, post, BaseResponse.class, false, false, MobileTestActivity.this);
            }
        } else if (tag.equals(updateInfoTask)) {
            User loginUser = MyApplication.getInstance().getLoginUser();
            loginUser.setPhone(mobileStr);
            MyApplication.getInstance().setLoginuser(loginUser);
            MyApplication.getInstance().userInfoChange = true;
            back();
        }
    }
}
