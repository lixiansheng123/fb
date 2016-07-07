package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.ui.fragment.AttentionDataFragment;
import com.yuedong.football_mad.ui.fragment.AttentionStarFragment;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

public class MyAttentionActivity extends BaseActivity {
    @ViewInject(R.id.tab)
    private DoubleTabView doubleTabView;
    private AttentionStarFragment attentionStarFragment;
    private AttentionDataFragment attentionDataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle3(R.drawable.ic_round_return, "我的关注", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_my_attention);
        initFragment();
        if (savedInstanceState == null) {
            addFragment(attentionStarFragment, R.id.fragment_container, false);
        }
    }

    private void initFragment() {
        attentionStarFragment = new AttentionStarFragment();
        attentionDataFragment = new AttentionDataFragment();
    }

    @Override
    protected Fragment getDefaultFrag() {
        return attentionStarFragment;
    }

    @Override
    protected void ui() {
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    switchContent(mDisplayContext, attentionStarFragment, R.id.fragment_container);
                } else {
                    switchContent(mDisplayContext, attentionDataFragment, R.id.fragment_container);
                }
            }
        });
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
