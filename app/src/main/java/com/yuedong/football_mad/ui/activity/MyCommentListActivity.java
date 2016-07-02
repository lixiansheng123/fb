package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyCommentListAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MyCommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "我的评论", R.drawable.ic_white_write_comment2, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }),R.layout.layout_common_list);
    }

    @Override
    protected void ui() {
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        MyCommentListAdapter adapter = new MyCommentListAdapter(this,data);
        listView.setAdapter(adapter);

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

}
