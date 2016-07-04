package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CompetitionListAdapter;
import com.yuedong.football_mad.adapter.GameListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.DbAttention;
import com.yuedong.football_mad.model.bean.GameListBean;
import com.yuedong.football_mad.model.bean.GameListRespBean;
import com.yuedong.football_mad.model.bean.IdAbbrnameLogoBean;
import com.yuedong.football_mad.model.bean.IdAbbrnameLogoRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
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
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchBallActivity extends BaseActivity implements View.OnClickListener {
    private String gameTask;
    @ViewInject(R.id.listview2)
    private ListView listView;
    @ViewInject(R.id.btn_drop)
    private ImageView ivDrop;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView pulltoRefreshListView;
    @ViewInject(R.id.btn_drop)
    private ImageView btnDrop;
    @ViewInject(R.id.tv_green_month)
    private TextView tvMonth;
    @ViewInject(R.id.tv_green_week)
    private TextView tvWeek;
    @ViewInject(R.id.tv_green_day)
    private TextView tvDay;
    @ViewInject(R.id.iv_refresh)
    private ImageView ivRefresh;
    private DaySelectPop daySelectPop;
    private String competitionId;// 赛事id
    private String gameDate;// 比赛时间
    private static final int ACTION_MY_COLLECT = 0x001;// 我的关注
    private static final int ACTION_FOCUS = 0x002;// 焦点
    private static final int ACTION_ITEM = 0x003;// ITEM CICK
    private int curAction = ACTION_MY_COLLECT;
    private int page;
    private RefreshProxy<GameListBean> refreshProxy = new RefreshProxy<>();
    private CompetitionListAdapter sideAdapter;
    private View sel1,sel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_watch_ball);
        daySelectPop = new DaySelectPop(this);
        autoLoadView = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
       boolean change =  MyApplication.getInstance().attentionGameChange;
        if(change){
            change = false;
            if(curAction == ACTION_MY_COLLECT)
                getGameList();
        }
    }

    @Override
    protected void ui() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_game_list, null);
        headerView.findViewById(R.id.ll_my_collect).setOnClickListener(this);
        headerView.findViewById(R.id.ll_item).setOnClickListener(this);
        sel1 = headerView.findViewById(R.id._sel);
        sel2 = headerView.findViewById(R.id.sel);
        listView.addHeaderView(headerView, null, false);
        getCompetition();
        daySelectPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivDrop.setImageResource(R.drawable.ic_green_down);
                gameDate = daySelectPop.getSelYear() + "-" + daySelectPop.getSelMonth() + "-" + daySelectPop.getSelDay();
                String week = DateUtils.getWeekByDate(DateUtils.parseString(gameDate, new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd)), "周");
                setTimeNavText(Integer.parseInt(daySelectPop.getSelMonth()), Integer.parseInt(daySelectPop.getSelDay()), week);
                getGameList();
            }
        });
        getGameList();
    }

    // 获取赛事
    private void getCompetition() {
        Map<String, String> post = new HashMap<>();
        post.put("count", "100");
        post.put("pageindex", "0");
        gameTask = RequestHelper.post(Constant.URL_ORDER_GAME_LIST, post, IdAbbrnameLogoRespBean.class, false, false, this);
    }

    private void getGameList() {
        // ui控制
        if (curAction == ACTION_FOCUS || curAction == ACTION_MY_COLLECT) {
            btnDrop.setClickable(false);
            ViewUtils.invisibleLayout(btnDrop);
            pulltoRefreshListView.setIsLoad(false);
            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            setTimeNavText(month, day, DateUtils.getWeekByDate(new Date(), "周"));
        } else if (curAction == ACTION_ITEM) {
            btnDrop.setClickable(true);
            ViewUtils.showLayout(btnDrop);
            pulltoRefreshListView.setIsLoad(true);
        }
        refreshProxy.setEmptyUi();
        refreshProxy.setEmpty();
        refreshProxy.setPulltoRefreshRefreshProxy(pulltoRefreshListView, new RefreshProxy.ProxyRefreshListener<GameListBean>() {
            @Override
            public BaseAdapter<GameListBean> getAdapter(List<GameListBean> data) {
                return new GameListAdapter(activity, data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if (type == 1) useCache = true;
                Map<String, String> post = new HashMap<String, String>();
                post.put("count", count + "");
                post.put("pageindex", page + "");
                // 接口控制
                if (curAction == ACTION_FOCUS) {
                    RequestHelper.post(Constant.URL_HOT_GAME_LIST, null, GameListRespBean.class, true, useCache, listener);
                } else if (curAction == ACTION_ITEM) {
                    if (gameDate == null) {
                        gameDate = DateUtils.getCurrentDate();
                    }
                    post.put("gameid", competitionId);
                    post.put("contesttime", gameDate);
                    RequestHelper.post(Constant.URL_GET_CONTEST_LIST, post, GameListRespBean.class, true, useCache, listener);
                } else if (curAction == ACTION_MY_COLLECT) {
                    List<DbAttention> gameList = DataUtils.getMyAttentionGameList();
                    if (gameList != null && !gameList.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (DbAttention dbAttention : gameList) {
                            sb.append(dbAttention.getId() + ",");
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        post.put("contestids", sb.toString());
                        RequestHelper.post(Constant.URL_GET_INTEREST_CONTEST, post, GameListRespBean.class, false, false, listener);
                    } else {
                        pulltoRefreshListView.onRefreshComplete();
                        T.showShort(activity, "暂无关注的比赛");
                    }
                }
            }

            @Override
            public void networkSucceed(ListResponse<GameListBean> list) {
                if (curAction == ACTION_FOCUS || curAction == ACTION_MY_COLLECT || list == null)
                    return;
                if (list.getDataList() != null && !list.getDataList().isEmpty()) {
                    GameListBean gameListBean = list.getDataList().get(0);
                    String contesttime = gameListBean.getContesttime();
                    if (!contesttime.equals(gameDate)) {
                        Date date = DateUtils.parseString(contesttime, new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd));
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        setTimeNavText(c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), DateUtils.getWeekByDate(date, "周"));
                    }
                }
            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }

    @OnItemClick(value = R.id.listview2)
    protected void itemClickEvent(AdapterView<?> parent, View view, int position, long id) {
        sideAdapter.setSelPos(position-1);
        ViewUtils.hideLayout(sel1);
        ViewUtils.hideLayout(sel2);
        curAction = ACTION_ITEM;
        IdAbbrnameLogoBean bean = (IdAbbrnameLogoBean) parent.getAdapter().getItem(position);
        competitionId = bean.getId();
        getGameList();
    }

    @OnItemClick(value = R.id.listview)
    protected void gameListItemClickEvent(AdapterView<?> parent, View view, int position, long id){
        GameListBean bean = (GameListBean) parent.getAdapter().getItem(position);
        Bundle data = new Bundle();
        data.putBoolean(Constant.KEY_BOOL,bean.attention);
        data.putString(Constant.KEY_ID, bean.getId());
        LaunchWithExitUtils.startActivity(activity,GameDetailActivity.class,data);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(gameTask)) {
            IdAbbrnameLogoRespBean bean = (IdAbbrnameLogoRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(List<IdAbbrnameLogoBean> list) {
        sideAdapter = new CompetitionListAdapter(this, list);
        listView.setAdapter(sideAdapter);
    }

    @OnClick({R.id.btn_left, R.id.btn_drop, R.id.iv_refresh})
    protected void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                back();
                break;
            case R.id.btn_drop:
                if (!daySelectPop.isShowing()) {
                    ivDrop.setImageResource(R.drawable.ic_green_confirm);
                    daySelectPop.showPop(view);
                } else {
                    daySelectPop.dismiss();
                }
                break;

            case R.id.iv_refresh:
                if (!rotateFinished) return;
                Animation rotate = animRotate();
                view.startAnimation(rotate);
                getGameList();
                break;
        }

    }

    private boolean rotateFinished = true;// 动画是否完成

    public Animation animRotate() {
        RotateAnimation ra = new RotateAnimation(0f, 1800f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        ra.setDuration(2000);
        ra.setRepeatMode(Animation.RESTART);
        // 先加速后减速
        ra.setInterpolator(new AccelerateDecelerateInterpolator());
        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rotateFinished = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rotateFinished = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return ra;
    }

    private void setTimeNavText(int month, int day, String week) {
        tvMonth.setText(month + "月");
        tvDay.setText(day + "日");
        tvWeek.setText(week);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_collect:
                sideAdapter.setSelPos(-1);
                ViewUtils.showLayout(sel1);
                ViewUtils.hideLayout(sel2);
                curAction = ACTION_MY_COLLECT;
                getGameList();
                break;

            case R.id.ll_item:
                sideAdapter.setSelPos(-1);
                ViewUtils.hideLayout(sel1);
                ViewUtils.showLayout(sel2);
                curAction = ACTION_FOCUS;
                getGameList();
                break;
        }
    }
}
