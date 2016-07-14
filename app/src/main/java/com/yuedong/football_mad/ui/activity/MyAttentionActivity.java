package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

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
    private boolean isEdit;
    private ImageView ivTitle3Right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle3(R.drawable.ic_round_return, "我的关注", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdit){
                    isEdit = true;
                    ivTitle3Right.setImageResource(R.drawable.ic_green_finish);
                }else{
                    isEdit = false;
                    ivTitle3Right.setImageResource(R.drawable.ic_white_edit);
                }
                attentionStarFragment.editList(isEdit);
                attentionDataFragment.editList(isEdit);
            }
        }), R.layout.activity_my_attention);
        ivTitle3Right = titleViewHelper.getTitle3Right();
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
