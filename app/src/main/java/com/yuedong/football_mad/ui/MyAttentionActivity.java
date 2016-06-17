package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyAttentionAdapter;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MyAttentionActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_my_attention);
    }

    @Override
    protected void ui() {
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        MyAttentionAdapter adapter = new MyAttentionAdapter(this,data);
        listView.setAdapter(adapter);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
