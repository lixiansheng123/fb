package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private View curTag;
    private String id;
    private String listTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(new TitleViewHelper(this).getTitle1NonentityCenter(R.drawable.ic_round_return, R.drawable.ic_white_write_comment, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_comment_list);
    }

    @Override
    protected void ui() {
        getCommentList();
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        CommentListAdapter adapter = new CommentListAdapter(this, data);
        listView.setAdapter(adapter);
    }

    private void getCommentList() {
        Map<String,String> post = new HashMap<>();
        post.put("newsid",id);
        post.put("count",10+"");
        post.put("pageindex",1+"");
        post.put("order",1+"");
        post.put("max",0+"");
        listTask = RequestHelper.post(Constant.URL_GET_COMMENT_BY_NEWS,post,null,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(listTask)){

        }
    }


}
