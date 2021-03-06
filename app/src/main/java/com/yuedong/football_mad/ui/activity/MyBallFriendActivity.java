package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.OnNotifyListener;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.ui.fragment.MyFansFragment;
import com.yuedong.football_mad.ui.fragment.MyFriendFragment;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.ViewUtils;

public class MyBallFriendActivity extends BaseActivity implements OnNotifyListener {
    private MyFriendFragment myFriendFragment;
    private MyFansFragment myFansFragment;
    @ViewInject(R.id.tab)
    private DoubleTabView tabView;
    private ImageView ivTitle3Right;
    private  boolean isEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle3(R.drawable.ic_round_return, "我的球友", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdit){
                    isEdit = true;
                    ivTitle3Right.setImageResource(R.drawable.ic_green_finish);
                }else{
                    isEdit = false;
                    ivTitle3Right.setImageResource(R.drawable.ic_white_edit);
                }
                myFriendFragment.editList(isEdit);
            }
        }), R.layout.activity_my_ball_friend);
        ivTitle3Right = titleViewHelper.getTitle3Right();
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
                    ViewUtils.showLayout(ivTitle3Right);
                    switchContent(mDisplayContext, myFriendFragment, R.id.fragment_container);
                }else{
                    ViewUtils.hideLayout(ivTitle3Right);
                    switchContent(mDisplayContext, myFansFragment, R.id.fragment_container);
                }
            }
        });

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @Override
    public void onNotifyDeleteFriend() {
        // 删除了粉丝粉丝列表
        myFansFragment.ui();
    }

    @Override
    public void onNotifyAddFriend() {
        // 添加了粉丝刷新好友列表
        myFriendFragment.getFriendList();

    }

}
