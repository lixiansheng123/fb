package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.SystemMsgAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.SystemMsgBean;
import com.yuedong.football_mad.model.bean.SystemMsgRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/13
 */
public class SystemMsgFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<SystemMsgBean> refreshProxy = new RefreshProxy<>();
    private User loginUser;
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
        loginUser = MyApplication.getInstance().getLoginUser();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<SystemMsgBean>() {
            @Override
            public BaseAdapter<SystemMsgBean> getAdapter(List<SystemMsgBean> data) {
                return new SystemMsgAdapter(getActivity(),data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                RequestHelper.post(Constant.URL_USER_SYSTEMMSG, DataUtils.getListPostMapHasSId(page+"",count+"",max+"",loginUser.getSid()), SystemMsgRespBean.class,true,useCache,listener);
            }

            @Override
            public void networkSucceed(ListResponse<SystemMsgBean> list) {

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
