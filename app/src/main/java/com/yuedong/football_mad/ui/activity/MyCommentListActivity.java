package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyCommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.MyCommentBean;
import com.yuedong.football_mad.model.bean.MyCommentRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.List;

public class MyCommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<MyCommentBean> refreshProxy = new RefreshProxy<>();
    private User loginUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "我的评论", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }),R.layout.layout_common_list);
    }

    @Override
    protected void ui() {
        loginUser = MyApplication.getInstance().getLoginUser();
        getCommentList();

    }

    private void getCommentList() {
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<MyCommentBean>() {
            @Override
            public BaseAdapter<MyCommentBean> getAdapter(List<MyCommentBean> data) {
                return new MyCommentListAdapter(activity,data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                RequestHelper.post(Constant.URL_COMMENT_LIST_BY_USER, DataUtils.getListPostMapHasUserId(page + "", count + "", max + "", loginUser.getId()), MyCommentRespBean.class,true,useCache,listener);
            }

            @Override
            public void networkSucceed(ListResponse<MyCommentBean> list) {

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
