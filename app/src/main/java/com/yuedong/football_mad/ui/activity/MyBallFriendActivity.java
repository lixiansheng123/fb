package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.ui.fragment.MyFansFragment;
import com.yuedong.football_mad.ui.fragment.MyFriendFragment;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.ViewUtils;

public class MyBallFriendActivity extends BaseActivity {
    private MyFriendFragment myFriendFragment;
    private MyFansFragment myFansFragment;
    @ViewInject(R.id.tab)
    private DoubleTabView tabView;
    private TextView tvTitle3Right;
    private  boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle3(R.drawable.ic_round_return, "我的球友", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdit){
                    isEdit = true ;
                    tvTitle3Right.setText("");
                    tvTitle3Right.setBackgroundResource(R.drawable.ic_green_finish);
                }else{
                    isEdit = false;
                    tvTitle3Right.setText("编辑");
                    tvTitle3Right.setBackgroundResource(R.drawable.bg_round_borderwhite1dp);
                }
            }
        }), R.layout.activity_my_ball_friend);
        tvTitle3Right = titleViewHelper.getTitle3Right();
        initFragment();
        if(savedInstanceState == null)
            addFragment(myFriendFragment,R.id.fragment_container,false);
    }

    @Override
    protected Fragment getDefaultFrag() {
        return myFriendFragment;
    }

    private void initFragment() {
        myFriendFragment = new MyFriendFragment();
        myFansFragment = new MyFansFragment();
    }

    @Override
    protected void ui() {
        tabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == 0){
                    ViewUtils.showLayout(tvTitle3Right);
                    switchContent(mDisplayContext, myFriendFragment, R.id.fragment_container);
                }else{
                    ViewUtils.hideLayout(tvTitle3Right);
                    switchContent(mDisplayContext, myFansFragment, R.id.fragment_container);
                }
            }
        });

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
