package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyMsgAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.ui.fragment.SystemMsgFragment;
import com.yuedong.football_mad.ui.fragment.UserMsgFragment;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MyMsgActivity extends BaseActivity {
    @ViewInject(R.id.tab)
    private DoubleTabView tabView;
    private UserMsgFragment userMsgFragment;
    private SystemMsgFragment systemMsgFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "我的消息", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_my_msg);
        initFragment();
        if(savedInstanceState == null){
            addFragment(userMsgFragment,R.id.fragment_container,false);
        }
    }

    @Override
    protected Fragment getDefaultFrag() {
        return userMsgFragment;
    }

    private void initFragment() {
        userMsgFragment = new UserMsgFragment();
        systemMsgFragment = new SystemMsgFragment();
    }

    @Override
    protected void ui() {
        tabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == 0){
                    switchContent(mDisplayContext,userMsgFragment,R.id.fragment_container);
                }else {
                    switchContent(mDisplayContext,systemMsgFragment,R.id.fragment_container);
                }
            }
        });
//        List<User> data = new ArrayList<>();
//        data.add(null);
//        data.add(null);
//        data.add(null);
//        MyMsgAdapter adapter = new MyMsgAdapter(this,data);
//        listView.setAdapter(adapter);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
