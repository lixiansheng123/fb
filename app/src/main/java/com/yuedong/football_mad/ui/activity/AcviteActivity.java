package com.yuedong.football_mad.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnItemClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

import java.util.List;
import java.util.Map;

/**
 * 活动
 */
public class AcviteActivity extends BaseActivity {
    private RefreshProxy<TouchBean> refreshProxy = new RefreshProxy<>();
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "活动", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_acvite);
    }

    @Override
    protected void ui() {
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new TouchAdapter(activity, data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if (type == 1) useCache = true;
                Map<String, String> post = DataUtils.getListPostMap(page + "", count + "", max + "");
                post.put("type", "4");
                RequestHelper.post(Constant.URL_NEWS_LIST, post, TouchListRespBean.class, true, useCache, listener);
            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }


    @OnItemClick(R.id.listview)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        TouchBean bean = (TouchBean) parent.getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_ID,bean.getId());
        LaunchWithExitUtils.startActivity(activity, NewsDetailActivity.class, bundle);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
