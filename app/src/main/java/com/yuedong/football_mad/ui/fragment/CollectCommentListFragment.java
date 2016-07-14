package com.yuedong.football_mad.ui.fragment;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CollectCommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.CollectCommentBean;
import com.yuedong.football_mad.model.bean.CollectCommentRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.T;

import java.util.List;

/**
 * @author 俊鹏 on 2016/7/11
 */
public class CollectCommentListFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<CollectCommentBean> refreshProxy = new RefreshProxy<>();
    private User loginUser;
    private CollectCommentListAdapter adapter;
    private CollectCommentBean bean;
    private String cancelCollectTask;
    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_common_list;
    }


    public void editList(boolean edit){
        if(!createUi)return;
        adapter.isEdit = edit;
        adapter.notifyDataSetChanged();
    }


    @Override
    public void ui() {
        loginUser = MyApplication.getInstance().getLoginUser();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<CollectCommentBean>() {
            @Override
            public BaseAdapter<CollectCommentBean> getAdapter(List<CollectCommentBean> data) {
                adapter = new CollectCommentListAdapter(getActivity(),data);
                adapter.setOnDeleteListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         bean = (CollectCommentBean) v.getTag();
                        cancelCollectTask =  DataUtils.cancelCollectComment(loginUser.getSid(),bean.getId(),CollectCommentListFragment.this);

                    }
                });
                return adapter;
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                cancelCollectTask =  RequestHelper.post(Constant.URL_USER_COLLECT_COMMENT_LIST, DataUtils.getListPostMapHasSId(page + "", count + "", max + "", loginUser.getSid()), CollectCommentRespBean.class,true,useCache,listener);
            }

            @Override
            public void networkSucceed(ListResponse<CollectCommentBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(cancelCollectTask)){
            adapter.getData().remove(bean);
            adapter.notifyDataSetChanged();
        }
    }
}
