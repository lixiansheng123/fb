package com.yuedong.football_mad.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.DataKuListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.bean.DataKuListRespBean;
import com.yuedong.football_mad.model.bean.FinalCompetitionBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnItemClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 俊鹏 on 2016/6/22
 */
public class DataKuListFragment extends BaseFragment {
    public static final int ACTION_COMPETITION = 0x001;
    public static final int ACTION_TEAM = 0x002;
    public static final int ACTION_ATHLETE = 0x003;
    public static final int ACTION_COUNTRY = 0x004;
    public static final int ACTION_OTHER = 0x005;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<FinalCompetitionBean> refreshProxy = new RefreshProxy<>();
    private String hotTask;
    private DataKuListAdapter adapter;
    private int action;
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
        action = getArguments().getInt(Constant.KEY_ACTION,ACTION_COMPETITION);
        getHotList();
    }

    @OnItemClick(value = R.id.listview)
    protected void itemClick(AdapterView<?> parent, View view, int position, long id){
        FinalCompetitionBean bean = (FinalCompetitionBean) parent.getAdapter().getItem(position);
        if(bean == null)return;
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_ID,bean.getId());
        bundle.putString(Constant.KEY_STR,bean.getAbbrname());
        Class<? extends Activity> cls = null;
        switch(action){
            case ACTION_COMPETITION:
                cls = CompetitionDetailActivity.class;
                break;
            case ACTION_TEAM:break;
            case ACTION_ATHLETE:break;
            case ACTION_COUNTRY:break;
            case ACTION_OTHER:break;
        }
        if(cls != null)
           LaunchWithExitUtils.startActivity(getActivity(),cls,bundle);
    }

    /**
     * 获取热门赛事
     */
    private void getHotList() {
        String url = "";
        if(action == ACTION_COMPETITION)
            url = Constant.URL_GET_HOTGAME;
        else if(action == ACTION_TEAM)
            url = Constant.URL_GET_HOTTEAM;
        else if(action == ACTION_ATHLETE)
            url = Constant.URL_GET_HOTATHLETE;
        else if(action == ACTION_COUNTRY)
            url = Constant.URL_GET_HOTCOUNTRY;
        else if(action == ACTION_OTHER)
            url = Constant.URL_GET_HOTOTHER;
        hotTask = RequestHelper.post(url,null, DataKuListRespBean.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(hotTask)){
            DataKuListRespBean bean = (DataKuListRespBean) data;
            final List<FinalCompetitionBean> hotList = bean.getData().getList();
            for (FinalCompetitionBean finalCompetitionBean : hotList){
                finalCompetitionBean.setIsHot(true);
            }
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<FinalCompetitionBean>() {
                @Override
                public BaseAdapter<FinalCompetitionBean> getAdapter(List<FinalCompetitionBean> data) {
                    return adapter = new DataKuListAdapter(getActivity(),data);
                }

                @Override
                public void executeTask(int page, int count, int max, final VolleyNetWorkCallback listener, final int type) {
                    String url = "";
                    if(action == ACTION_COMPETITION)
                        url = Constant.URL_GET_GAMELIST;
                    else if(action == ACTION_TEAM)
                        url = Constant.URL_GET_TEAM_LIST;
                    else if(action == ACTION_ATHLETE)
                        url = Constant.URL_GET_ATHLETELIST;
                    else if(action == ACTION_COUNTRY)
                        url = Constant.URL_GET_COUNTRYLIST;
                    else if(action == ACTION_OTHER)
                        url = Constant.URL_GET_OTHERLIST;
                    Map<String,String> post = new HashMap<String, String>();
                    post.put("count",count+"");
                    post.put("pageindex",page+"");
                    boolean useCache = false;
                    if(type == 1)
                        useCache = true;
                    RequestHelper.post(url, post, DataKuListRespBean.class, true, useCache, new VolleyNetWorkCallback() {
                        @Override
                        public void onNetworkStart(String tag) {
                            listener.onNetworkStart(tag);
                        }

                        @Override
                        public void onNetworkSucceed(String tag, BaseResponse data) {
                            DataKuListRespBean listRespBean = (DataKuListRespBean) data;
                            List<FinalCompetitionBean> dataList = listRespBean.getDataList();
                            for (FinalCompetitionBean bean1 : dataList){
                                bean1.setIsHot(false);
                            }
                            if(type == 1){
                                List<FinalCompetitionBean> list = new ArrayList<FinalCompetitionBean>();
                                list.addAll(hotList);
                                list.addAll(dataList);
                                listRespBean.getData().setList(list);
                            }
                            listener.onNetworkSucceed(tag,data);
                        }

                        @Override
                        public void onNetworkError(String tag, VolleyError error) {
                            listener.onNetworkError(tag,error);
                        }
                    });
                }

                @Override
                public void networkSucceed(ListResponse<FinalCompetitionBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });
        }
    }
}
