package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.BallPlayerAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.NewWithUserInfoBean;
import com.yuedong.football_mad.model.bean.NewWithUserInfoRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallplayerActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private String hotList;
    private boolean one = true;
    private RefreshProxy<NewWithUserInfoBean> refreshProxy = new RefreshProxy<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "球坛", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.layout_common_list);
        ViewUtils.hideLayout(titleViewHelper.getTitle1Left());
    }

    @Override
    protected void ui() {
        Map<String,String> post = new HashMap<>();
        post.put("type","4");
        hotList =   RequestHelper.post(Constant.URL_QIUTAN_HOTLIST,post,NewWithUserInfoRespBean.class,true,true,BallplayerActivity.this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(hotList)){
            NewWithUserInfoRespBean bean = (NewWithUserInfoRespBean) data;
            final List<NewWithUserInfoBean> hotList = bean.getDataList();
            for(NewWithUserInfoBean b : hotList){
                b.setHot(true);
            }
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<NewWithUserInfoBean>() {
                @Override
                public BaseAdapter<NewWithUserInfoBean> getAdapter(List<NewWithUserInfoBean> data) {
                    return new BallPlayerAdapter(activity,data);
                }

                @Override
                public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, int type) {
                    boolean useCache = false;
                    if(type == 1){
                        one = true;
                        useCache = true;
                    }
                    Map<String, String> post = DataUtils.getListPostMap(page + "", count + "", max + "");
                    post.put("type","4");
                    RequestHelper.post(Constant.URL_QIUTAN_NEWLIST, post, NewWithUserInfoRespBean.class, true, useCache, new VolleyNetWorkCallback() {
                        @Override
                        public void onNetworkStart(String tag) {
                            listener.onNetworkStart(tag);
                        }

                        @Override
                        public void onNetworkSucceed(String tag, BaseResponse data) {
                            NewWithUserInfoRespBean bean1 = (NewWithUserInfoRespBean) data;
                            List<NewWithUserInfoBean> list = new ArrayList<NewWithUserInfoBean>();

                            if(one){
                                list.addAll(hotList);
                            }
                            list.addAll(bean1.getDataList());
                            bean1.getData().setList(list);
                            listener.onNetworkSucceed(tag, bean1);
                        }

                        @Override
                        public void onNetworkError(String tag, VolleyError error) {
                            listener.onNetworkError(tag, error);
                        }
                    });
                }

                @Override
                public void networkSucceed(ListResponse<NewWithUserInfoBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });

        }
    }
}
