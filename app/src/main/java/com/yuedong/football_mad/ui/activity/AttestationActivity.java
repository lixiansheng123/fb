package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;

public class AttestationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterIcon(R.drawable.ic_round_return, R.drawable.ic_renzheng_logo, R.drawable.ic_round_renzheng, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        }, null), R.layout.activity_attestation);
    }

    @Override
    protected void ui() {
        findViewById(R.id.item_top).setVisibility(View.VISIBLE);
        findViewById(R.id.line).setVisibility(View.GONE);
        findViewById(R.id.iv_icon).setVisibility(View.GONE);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
