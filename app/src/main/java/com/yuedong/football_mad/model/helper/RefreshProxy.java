package com.yuedong.football_mad.model.helper;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.List;

public class RefreshProxy<T> {
    public BaseAdapter<T> adapter;
    public List<T> datas = new ArrayList<T>();
    public PulltoRefreshListView listView;
    public int currentPager;
    private android.os.Handler handler = new android.os.Handler();

    public BaseAdapter<T> getAdapter() {
        return adapter;
    }

    public boolean refresh = false;
    public boolean autoRefreshAnim = true;
    private boolean one = true;
    private int max;

    public PulltoRefreshListView getListView() {
        return listView;
    }

    private ProxyRefreshListener<T> mProxyRefreshListener;

    /**
     * 设置pulltorefresh代理刷新和加载更多
     *
     * @param listView
     * @param proxyRefreshListener
     */
    public void setPulltoRefreshRefreshProxy(PulltoRefreshListView listView, final ProxyRefreshListener<T> proxyRefreshListener) {
        max = 0;
        this.listView = listView;
        if (listView == null || proxyRefreshListener == null) return;
        this.mProxyRefreshListener = proxyRefreshListener;
        adapter = proxyRefreshListener.getAdapter(datas);
        if (adapter != null)
            listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PulltoRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!refresh) refresh = true;
                max = 0;
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                executeNetworkTask(1, proxyRefreshListener);
//                    }
//                }, 500);
                if (one) {
                    one = false;
                    refresh = false;
                }
            }

            @Override
            public void onLoad() {
                if (!refresh) refresh = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        executeNetworkTask(2, proxyRefreshListener);
                    }
                }, 500);
            }
        });
        if(autoRefreshAnim){
            // 进入自动刷新
            listView.autoRefresh();
        }else{
            listView.onRefresh();
        }
    }

    /**
     * 刷新  头部  静态(splistview 不会发生样式变化)
     */
    public void staticRefresh() {
        if (!refresh) refresh = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                executeNetworkTask(1, mProxyRefreshListener);
            }
        }, 500);
        if (one) {
            one = false;
            refresh = false;
        }
    }

    /**
     * @param mode                 1正常的刷新和下拉刷新 2上拉更多
     * @param proxyRefreshListener
     */
    protected void executeNetworkTask(final int mode, final ProxyRefreshListener<T> proxyRefreshListener) {
        L.d("executeTask-onStart");
        int skip = 0;
        final int count = Config.PAGER_SIZE;
        if (mode == 2) {
            currentPager++;
//            skip = (currentPager - 1) * count;
        } else if (mode == 1) {
            refreshStatus();
        }
        proxyRefreshListener.executeTask(currentPager, count, max, new VolleyNetWorkCallback() {
            @Override
            public void onNetworkStart(String tag) {

            }

            @Override
            public void onNetworkSucceed(String tag, BaseResponse data) {
                ListResponse<T> list = null;
                if (data instanceof ListResponse)
                    list = (ListResponse) data;
                L.d("executeTask- onFinish");
                listView.onRefreshComplete();
                proxyRefreshListener.networkSucceed(list);
                if (list != null && !list.getDataList().isEmpty()) {
                    max = list.getData().getMax();
                    updateData(list.getDataList(), mode);
                } else {
                    listView.setLoadFull();
                    if (mode == 1) {
                        proxyRefreshListener.contentIsEmpty();
                    }
                }
            }

            @Override
            public void onNetworkError(String tag, VolleyError error) {
                listView.onRefreshComplete();
                L.d("executeTask-onError");
                if (error != null) {
                    com.yuedong.lib_develop.utils.T.showShort(App.getInstance().getAppContext(), error.getMessage());
                }
            }
        }, mode);
    }

    private void updateData(List<T> list, int mode) {
        if (list.size() < Config.PAGER_SIZE) {
            listView.setLoadFull();
        }
        if (mode == 1) {
            datas.clear();
            datas.addAll(list);
        } else
            datas.addAll(list);
        adapter.notifyDataSetChanged();
    }


    public void refreshStatus() {
//        datas.clear();
        currentPager = 1;
    }

    public void setEmptyUi() {
        L.d("setEmptyUi---------------->");
        if (adapter == null) return;
        datas.clear();
        adapter.notifyDataSetChanged();
    }

    public void setEmpty() {
        L.d("setEmpty---------------->");
        adapter = null;
        listView = null;
    }


    public interface ProxyRefreshListener<T> {
        /**
         * 获取适配器
         */
        BaseAdapter<T> getAdapter(List<T> data);

        /**
         * 执行网络方法
         */
        void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type);

        /**
         * 成功回调
         */
        void networkSucceed(ListResponse<T> list);

        /**
         * 无内容回调
         */
        void contentIsEmpty();
    }
}
