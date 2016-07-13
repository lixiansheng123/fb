package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyArticleAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
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
import java.util.Map;

/**
 * 我的文章
 */
public class MyArticleActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<TouchBean> refreshProxy = new RefreshProxy<>();
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "我的文章", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.layout_common_list);
    }

    @Override
    protected void ui() {
        loginUser = MyApplication.getInstance().getLoginUser();
//        List<User> data = new ArrayList<User>();
//        data.add(null);
//        data.add(null);
//        data.add(null);
//        MyArticleAdapter adapter = new MyArticleAdapter(this, data);
//        listView.setAdapter(adapter);
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new MyArticleAdapter(activity,data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String, String> post = DataUtils.getListPostMapHasUserId(page + "", count + "", max + "", loginUser.getId());
                post.put("order","0");
                RequestHelper.post(Constant.URL_USER_NEWS, post, TouchListRespBean.class, false, false, listener);
            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

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
