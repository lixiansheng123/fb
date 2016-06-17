package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CommentListAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class CommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @ViewInject(R.id.iv_hot)
    private ImageView ivHot;
    @ViewInject(R.id.iv_new)
    private ImageView ivNew;
    private View curTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NonentityCenter(R.drawable.ic_round_return, R.drawable.ic_white_write_comment, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_comment_list);
    }

    @Override
    protected void ui() {
        ivHot.setSelected(true);
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        CommentListAdapter adapter = new CommentListAdapter(this, data);
        listView.setAdapter(adapter);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.iv_hot, R.id.iv_new})
    public void clickEvent(View view) {
        if (view == curTag) return;
        curTag = view;
        switch (view.getId()) {
            case R.id.iv_hot:
                ivHot.setSelected(true);
                ivNew.setSelected(false);
                break;
            case R.id.iv_new:
                ivHot.setSelected(false);
                ivNew.setSelected(true);
                break;
        }
    }
}
