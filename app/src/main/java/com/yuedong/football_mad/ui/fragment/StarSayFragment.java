package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.StartSayAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.StarSayBean;
import com.yuedong.football_mad.model.bean.StarSayRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.helper.DataUtils;
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
public class StarSayFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<StarSayBean> refreshProxy = new RefreshProxy<>();
    private User loginUser;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_star_say;
    }

    @Override
    public void ui() {
        autoLoadView =false;
        loginUser = MyApplication.getInstance().getLoginUser();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<StarSayBean>() {
            @Override
            public BaseAdapter<StarSayBean> getAdapter(List<StarSayBean> data) {
                return new StartSayAdapter(getActivity(),data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                Map<String,String> post = new HashMap<String, String>();
                String userId = "0";
                if(loginUser != null) userId = loginUser.getId();
                RequestHelper.post(Constant.URL_START_SAY_LIST,DataUtils.getListPostMapHasUserId(page+"",count+"",max+"",userId), StarSayRespBean.class,true,useCache,listener);
            }

            @Override
            public void networkSucceed(ListResponse<StarSayBean> list) {

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
