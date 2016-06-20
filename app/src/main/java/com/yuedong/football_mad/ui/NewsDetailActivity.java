package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;

public class NewsDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_news_detail);
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.iv_icon, R.id.iv_icon2, R.id.iv_icon3, R.id.title_btn_left, R.id.title_btn_right,R.id.btn_comment})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.iv_icon2:
                break;
            case R.id.iv_icon3:
                break;
            case R.id.title_btn_left:
                back();
                break;
            case R.id.title_btn_right:
                LaunchWithExitUtils.startActivity(NewsDetailActivity.this,CommentListActivity.class);
                break;
            case R.id.btn_comment:break;
        }
        T.showShort(NewsDetailActivity.this, "btn click...");
    }


}
