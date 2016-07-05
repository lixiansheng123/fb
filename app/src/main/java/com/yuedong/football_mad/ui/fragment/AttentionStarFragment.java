package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyAttentionStarAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.AttentionStarBean;
import com.yuedong.football_mad.model.bean.AttentionStarRespBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/7/5
 */
public class AttentionStarFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<AttentionStarBean> refreshProxy = new RefreshProxy<>();
    private String hotListTask;
    private User loginUser;
    private boolean one = true;

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
        getHotList();

    }

    private void getHotList() {
        User loginUser = MyApplication.getInstance().getLoginUser();
        hotListTask = RequestHelper.post(Constant.URL_NEWS_HOT_STAR, DataUtils.getUserIdPostMap(loginUser.getId()), AttentionStarRespBean.class, false, false, this);
    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(hotListTask)) {
            AttentionStarRespBean bean = (AttentionStarRespBean) data;
            final List<AttentionStarBean> hotList = bean.getDataList();
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<AttentionStarBean>() {
                @Override
                public BaseAdapter<AttentionStarBean> getAdapter(List<AttentionStarBean> data) {
                    return new MyAttentionStarAdapter(getActivity(), data);
                }

                @Override
                public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, int type) {
                    if (type == 1) one = true;
                    Map<String, String> post = DataUtils.getListPostMapHasSId(page + "", count + "", "", loginUser.getSid());
                    RequestHelper.post(Constant.URL_USER_ATTENTION_STAR, post, AttentionStarRespBean.class, false, false, new VolleyNetWorkCallback() {
                        @Override
                        public void onNetworkStart(String tag) {
                            listener.onNetworkStart(tag);
                        }

                        @Override
                        public void onNetworkSucceed(String tag, BaseResponse data) {
                            if (one) {
                                one = false;
                                AttentionStarRespBean attentionStarRespBean = (AttentionStarRespBean) data;
                                List<AttentionStarBean> finalList = new ArrayList<AttentionStarBean>();
                                finalList.addAll(hotList);
                                finalList.addAll(attentionStarRespBean.getDataList());
                                attentionStarRespBean.getData().setList(finalList);
                            }
                            listener.onNetworkSucceed(tag, data);

                        }

                        @Override
                        public void onNetworkError(String tag, VolleyError error) {
                            listener.onNetworkError(tag, error);
                        }
                    });
                }

                @Override
                public void networkSucceed(ListResponse<AttentionStarBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });
        }
    }
}
