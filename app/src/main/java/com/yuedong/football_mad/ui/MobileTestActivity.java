package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;

public class MobileTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NoRight(R.drawable.ic_round_return,R.drawable.ic_title_bar_logo,null),
                R.layout.activity_mobile_test);
    }

    @Override
    protected void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
