package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.CommentRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsCommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @ViewInject(R.id.doubleTabView)
    private DoubleTabView doubleTabView;
    private View curTag;
    private String id;
    private String listTask;
    private RefreshProxy<List<CommentBean>> refreshProxy = new RefreshProxy<>();
    private String order = "0";

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
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view,int position) {
                if(position == 0){
                    order = "0";
                    getCommentList();
                }else{
                    order = "1";
                    getCommentList();
                }
            }
        });
    }

    private void getCommentList() {
        refreshProxy.setEmptyUi();
        refreshProxy.setEmpty();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<List<CommentBean>>() {

            @Override
            public BaseAdapter<List<CommentBean>> getAdapter(List<List<CommentBean>> data) {
                return new CommentListAdapter(activity, data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String, String> post = new HashMap<>();
                post.put("newsid", id);
                post.put("count", count + "");
                post.put("pageindex", page + "");
                post.put("order", order);
                post.put("max", max + "");
                boolean useCache = false;
                if (type == 1)
                    useCache = true;
                listTask = RequestHelper.post(Constant.URL_GET_COMMENT_BY_NEWS, post, CommentRespBean.class, true, useCache, listener);
            }

            @Override
            public void networkSucceed(ListResponse<List<CommentBean>> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
    }
}
