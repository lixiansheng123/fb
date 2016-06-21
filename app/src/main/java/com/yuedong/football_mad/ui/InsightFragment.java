package com.yuedong.football_mad.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.InsihtAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.InsightBean;
import com.yuedong.football_mad.model.bean.InsightListRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class InsightFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @ViewInject(R.id.id_include_layout)
    private View stopView;
    private SupportScrollConflictListView spListView;
    private String hotListTask;
    private RefreshProxy<InsightBean> refreshProxy = new RefreshProxy<>();
    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_insight;
    }

    @Override
    public void ui() {
        stopView.setVisibility(View.GONE);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.listview,listView,false);
        spListView = (SupportScrollConflictListView) header.findViewById(R.id.spListView);
        listView.addHeaderView(header,null,false);
        getHotInsignList();
    }

    /**
     * 最后见地列表
     */
    private void getHotInsignList() {
        hotListTask =   RequestHelper.post(Constant.URL_HOT_INSIGNLIST,null, InsightListRespBean.class,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(hotListTask)){
            InsightListRespBean bean = (InsightListRespBean) data;
            InsihtAdapter hotAdapter = new InsihtAdapter(getActivity(),bean.getDataList());
            hotAdapter.setIsHot(true);
            spListView.setAdapter(hotAdapter);
            // 设置代理
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<InsightBean>() {
                @Override
                public BaseAdapter<InsightBean> getAdapter(List<InsightBean> data) {
                    return new InsihtAdapter(getActivity(),data);
                }

                @Override
                public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener) {
                    Map<String,String> post = new HashMap<String, String>();
                    post.put("pageindex",page+"");
                    post.put("count",count+"");
                    post.put("max",max+"");
                    RequestHelper.post(Constant.URL_INSIGNLIST,post,InsightListRespBean.class,true,listener);
                }

                @Override
                public void networkSucceed(ListResponse<InsightBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });

        }
    }


}
