package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.SixSixSixListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.SixSixSixBean;
import com.yuedong.football_mad.model.bean.SixSixSixRespBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class SixSixSixFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<SixSixSixBean> refreshProxy = new RefreshProxy<>();
    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_hone_sixsixsix;
    }

    @Override
    public void ui() {
        getList();
    }

    private void getList() {
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<SixSixSixBean>() {
            @Override
            public BaseAdapter<SixSixSixBean> getAdapter(List<SixSixSixBean> data) {
                return new SixSixSixListAdapter(getActivity(),data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                Map<String,String> post = new HashMap<String, String>();
                post.put("pageindex",page+"");
                post.put("max",max+"");
                post.put("count",count+"");
                RequestHelper.post(Constant.URL_NEWS_666_LIST,post, SixSixSixRespBean.class,true,useCache,listener);
            }

            @Override
            public void networkSucceed(ListResponse<SixSixSixBean> list) {

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
