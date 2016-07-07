package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyCollectAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.CollectListBean;
import com.yuedong.football_mad.model.bean.CollectListRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/7/7
 */
public class CollectNewsListFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<CollectListBean> refreshProxy = new RefreshProxy<>();

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_common_list;
    }

    @Override
    public void ui() {
        final User loginUser = MyApplication.getInstance().getLoginUser();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<CollectListBean>() {
            @Override
            public BaseAdapter<CollectListBean> getAdapter(List<CollectListBean> data) {
                return new MyCollectAdapter(getActivity(), data);
            }

            @Override
            public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, int type) {
                Map<String, String> post = DataUtils.getListPostMapHasSId(page + "", count + "", "", loginUser.getSid());
                RequestHelper.post(Constant.URL_COLLECT_NEWS_LIST, post, CollectListRespBean.class, false, false, new VolleyNetWorkCallback() {
                    @Override
                    public void onNetworkStart(String tag) {
                        listener.onNetworkStart(tag);
                    }

                    @Override
                    public void onNetworkSucceed(String tag, BaseResponse data) {
                        List<CollectListBean> list = new ArrayList<CollectListBean>();
                        CollectListRespBean bean = (CollectListRespBean) data;
                        List<CollectListBean> special = bean.getData().getList().getSpecial();
                        List<CollectListBean> news = bean.getData().getList().getNews();
                        if (special != null) {
                            for (CollectListBean b : special) {
                                b.type = 1;
                                list.add(b);
                            }
                        }
                        if (news != null) {
                            for (CollectListBean b : news) {
                                b.type = 2;
                                list.add(b);
                            }
                        }
                        bean.setDatas(list);
                        listener.onNetworkSucceed(tag, bean);
                    }

                    @Override
                    public void onNetworkError(String tag, VolleyError error) {
                        listener.onNetworkError(tag, error);
                    }
                });
            }

            @Override
            public void networkSucceed(ListResponse<CollectListBean> list) {

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
