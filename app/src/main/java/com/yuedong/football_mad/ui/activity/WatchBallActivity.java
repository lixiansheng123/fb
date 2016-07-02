package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CompetitionListAdapter;
import com.yuedong.football_mad.adapter.GameListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.bean.DataKuListRespBean;
import com.yuedong.football_mad.model.bean.GameListBean;
import com.yuedong.football_mad.model.bean.GameListRespBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.DaySelectPop;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.ioc.annotation.event.OnItemClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchBallActivity extends BaseActivity {
    private String gameTask;
    @ViewInject(R.id.listview2)
    private ListView listView;
    @ViewInject(R.id.btn_drop)
    private ImageView ivDrop;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView pulltoRefreshListView;
    private DaySelectPop daySelectPop;
    private String competitionId;// 赛事id
    private String gameDate;// 比赛时间
    private static final int ACTION_MY_COLLECT = 0x001;// 我的关注
    private static final int ACTION_FOCUS = 0x002;// 焦点
    private static final int ACTION_ITEM = 0x003;// ITEM CICK
    private int curAction = ACTION_FOCUS;
    private int page;
    private String listTask;
    private RefreshProxy<GameListBean> refreshProxy = new RefreshProxy<>();
//    private HotGameListFragment hotGameListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_watch_ball);
        daySelectPop = new DaySelectPop(this);
//        initFragment();
//        if(savedInstanceState == null)
//            addFragment(hotGameListFragment,R.id.fragment_container,false);
    }

//    @Override
//    protected Fragment getDefaultFrag() {
//        return hotGameListFragment;
//    }

//    private void initFragment() {
//        hotGameListFragment = new HotGameListFragment();
//    }

    @Override
    protected void ui() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_game_list, null);
        listView.addHeaderView(headerView, null, false);
        getCompetition();
        daySelectPop.setCallback(new CommonCallback() {
            @Override
            public void callbackYMR(String year, String month, String day) {
                gameDate = year + "-" + month + "-" + day;
                getGameList();
            }
        });
        pulltoRefreshListView.setOnRefreshListener(new PulltoRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getGameList();
            }

            @Override
            public void onLoad() {
                page++;
                getGameList();
            }
        });
        pulltoRefreshListView.autoRefresh();
    }

    // 获取赛事
    private void getCompetition() {
        Map<String, String> post = new HashMap<>();
        post.put("count", "100");
        post.put("pageindex", "0");
        gameTask = RequestHelper.post(Constant.URL_GAMELIST, post, DataKuListRespBean.class, false, false, this);

    }

    private void getGameList(){
        if(curAction == ACTION_FOCUS){
            pulltoRefreshListView.setIsLoad(false);
        }
        refreshProxy.setPulltoRefreshRefreshProxy(pulltoRefreshListView, new RefreshProxy.ProxyRefreshListener<GameListBean>() {
            @Override
            public BaseAdapter<GameListBean> getAdapter(List<GameListBean> data) {
                return new GameListAdapter(activity,data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if(type == 1)useCache = true;
                if(curAction == ACTION_FOCUS){
                     RequestHelper.post(Constant.URL_HOT_GAME_LIST,null, GameListRespBean.class,true,useCache,listener);
                }
            }

            @Override
            public void networkSucceed(ListResponse<GameListBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });



//        Map<String,String> post = new HashMap<>();
//        post.put("count", Config.PAGER_SIZE+"");
//        post.put("pageindex",page+"");
    }

    @OnItemClick(value = R.id.listview)
    protected void itemClickEvent(AdapterView<?> parent, View view, int position, long id){
        curAction = ACTION_ITEM;
//        parent.getAdapter().getItem()
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(gameTask)) {
            DataKuListRespBean bean = (DataKuListRespBean) data;
            renderUi(bean.getData().getList());
        }else if(tag.equals(listTask)){
            GameListRespBean bean = (GameListRespBean) data;
        }
    }

    private void renderUi(List<FinalCompetitionBean> list) {
        CompetitionListAdapter adapter = new CompetitionListAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.btn_left,R.id.ll_my_collect,R.id.ll_item,R.id.btn_drop})
    protected void clickEvent(View view) {
        switch(view.getId()){
            case R.id.btn_left:
                back();
                break;

            case R.id.ll_my_collect:
                curAction = ACTION_MY_COLLECT;
                getGameList();
                break;

            case R.id.ll_item:
                curAction = ACTION_FOCUS;
                getGameList();
                break;

            case R.id.btn_drop:
                if(!daySelectPop.isShowing()){
                    ivDrop.setImageResource(R.drawable.ic_green_confirm);
                    daySelectPop.showPop(view);
                }else{
                    ivDrop.setImageResource(R.drawable.ic_green_down);
                    daySelectPop.dismiss();
                    gameDate = daySelectPop.getSelYear()+"-"+daySelectPop.getSelMonth()+"-"+daySelectPop.getSelDay();
                    getGameList();
                }
                break;
        }
    }




}
