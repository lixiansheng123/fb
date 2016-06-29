package com.yuedong.football_mad.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.SpecialBottomAdapter;
import com.yuedong.football_mad.adapter.SpecialTopAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.HotSpecialListBean;
import com.yuedong.football_mad.model.bean.IdNameLogoBean;
import com.yuedong.football_mad.model.bean.SpecialBean;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.SpecialListBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class SpecialFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private GridView gridView;
    private SpecialTopAdapter specialTopAdapter;
    private String topTask;
    private List<IdNameLogoBean> topDatas = new ArrayList<IdNameLogoBean>();
    private RefreshProxy<SpecialBean> refreshProxy = new RefreshProxy<SpecialBean>();

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_special;
    }

    @Override
    public void ui() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_home_special_list, listView, false);
        gridView = (GridView) headView.findViewById(R.id.gv_hot_special);
        listView.addHeaderView(headView, null, false);
        specialTopAdapter = new SpecialTopAdapter(getActivity(), topDatas);
        gridView.setAdapter(specialTopAdapter);
        hotSpecialList();
        bottomSpecialList();
    }

    private void bottomSpecialList() {
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<SpecialBean>() {
            @Override
            public BaseAdapter<SpecialBean> getAdapter(List<SpecialBean> data) {
                return new SpecialBottomAdapter(getActivity(), data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("count", count + "");
                data.put("pageindex", page + "");
                boolean useCache = false;
                if (type == 1)
                    useCache = true;
                RequestHelper.post(Constant.URL_SPECIAL_LIST, data, SpecialListBean.class, true, useCache, listener);
            }

            @Override
            public void networkSucceed(ListResponse<SpecialBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }


    /**
     * 热门专题列表
     */
    private void hotSpecialList() {

        topTask = RequestHelper.post(Constant.URL_HOT_SPECIAL, null, HotSpecialListBean.class, true, true, this);
    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(topTask)) {
            HotSpecialListBean listBean = (HotSpecialListBean) data;
            topDatas.addAll(listBean.getData().getList());
            specialTopAdapter.notifyDataSetChanged();
        }
    }
}
