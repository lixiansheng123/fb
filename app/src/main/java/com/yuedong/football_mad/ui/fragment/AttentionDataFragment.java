package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.SearchAllAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.AttentionDataRespBean;
import com.yuedong.football_mad.model.bean.FinalSearchAllBean;
import com.yuedong.football_mad.model.bean.IdNameLogoBean;
import com.yuedong.football_mad.model.bean.SearchAllBean;
import com.yuedong.football_mad.model.bean.SearchAllRespBean;
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

/**
 * @author 俊鹏 on 2016/7/6
 */
public class AttentionDataFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<FinalSearchAllBean> refreshProxy = new RefreshProxy<>();

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
        final User user = MyApplication.getInstance().getLoginUser();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<FinalSearchAllBean>() {
            @Override
            public BaseAdapter<FinalSearchAllBean> getAdapter(List<FinalSearchAllBean> data) {
                SearchAllAdapter adapter = new SearchAllAdapter(getActivity());
                adapter.setEmptyData(data);
                return adapter;
            }

            @Override
            public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, int type) {
                RequestHelper.post(Constant.URL_USER_INTEREST_DATA, DataUtils.getListPostMapHasSId(page + "", count + "", "", user.getSid()), AttentionDataRespBean.class, false, false, new VolleyNetWorkCallback() {
                    @Override
                    public void onNetworkStart(String tag) {
                        listener.onNetworkStart(tag);
                    }

                    @Override
                    public void onNetworkSucceed(String tag, BaseResponse data) {
                        AttentionDataRespBean bean = (AttentionDataRespBean) data;
                        SearchAllRespBean.List list = bean.getData().getList();
                        List<FinalSearchAllBean> all = new ArrayList<>();
                        List<FinalSearchAllBean> finalAthlete = new ArrayList<>();
                        List<FinalSearchAllBean> finalCountry = new ArrayList<>();
                        List<FinalSearchAllBean> finalGame = new ArrayList<>();
                        List<FinalSearchAllBean> finalOther = new ArrayList<>();
                        List<FinalSearchAllBean> finalTeam = new ArrayList<>();
                        if (list.getAthlete() != null)
                            for (SearchAllBean a : list.getAthlete()) {
                                FinalSearchAllBean fa = new FinalSearchAllBean();
                                toBean(fa, a, 3);
                                finalAthlete.add(fa);
                            }
                        if (list.getCountry() != null)
                            for (SearchAllBean b : list.getCountry()) {
                                FinalSearchAllBean fa = new FinalSearchAllBean();
                                toBean(fa, b, 4);
                                finalCountry.add(fa);
                            }
                        if (list.getGame() != null)
                            for (SearchAllBean c : list.getGame()) {
                                FinalSearchAllBean fa = new FinalSearchAllBean();
                                toBean(fa, c, 1);
                                finalGame.add(fa);
                            }
                        if (list.getOther() != null)
                            for (SearchAllBean d : list.getOther()) {
                                FinalSearchAllBean fa = new FinalSearchAllBean();
                                toBean(fa, d, 5);
                                finalOther.add(fa);
                            }
                        if (list.getTeam() != null)
                            for (SearchAllBean e : list.getTeam()) {
                                FinalSearchAllBean fa = new FinalSearchAllBean();
                                toBean(fa, e, 2);
                                finalTeam.add(fa);
                            }
                        all.addAll(finalAthlete);
                        all.addAll(finalCountry);
                        all.addAll(finalGame);
                        all.addAll(finalOther);
                        all.addAll(finalTeam);
                        bean.setList2(all);
                        listener.onNetworkSucceed(tag, data);
                    }

                    @Override
                    public void onNetworkError(String tag, VolleyError error) {
                        listener.onNetworkError(tag, error);
                    }
                });
            }

            @Override
            public void networkSucceed(ListResponse<FinalSearchAllBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }

    private void toBean(FinalSearchAllBean fa, SearchAllBean b, int type) {
        fa.setName(b.getName());
        fa.setId(b.getId());
        fa.setLogo(b.getLogo());
        fa.setType(type);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
