package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyBallFriendAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MyBallFriendActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle3(R.drawable.ic_round_return, "我的球友", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_my_ball_friend);
    }

    @Override
    protected void ui() {
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        MyBallFriendAdapter adapter = new MyBallFriendAdapter(this,data);
        listView.setAdapter(adapter);

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
