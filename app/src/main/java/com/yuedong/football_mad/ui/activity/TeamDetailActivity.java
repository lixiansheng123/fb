package com.yuedong.football_mad.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TeamHeaderAdapter;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.TeamDetailBean;
import com.yuedong.football_mad.model.bean.TeamDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.ui.fragment.DataKuListFragment;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.view.SupportScrollConflictGridView;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDetailActivity extends BaseActivity {
    private String id;
    private String detailTask;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private SupportScrollConflictListView scoreListview;
    private RefreshProxy<TouchBean> refreshProxy = new RefreshProxy<>();
    @ViewInject(R.id.tv_white1)
    private TextView tvWhite1;
    @ViewInject(R.id.tv_white2)
    private TextView tvWhite2;
    @ViewInject(R.id.tv_white3)
    private TextView tvWhite3;
    @ViewInject(R.id.iv_logo)
    private NetworkImageView logo;
    @ViewInject(R.id.tv_rank)
    private TextView tvRank;
    @ViewInject(R.id.tv_grey1)
    private TextView tvGrey1;
    @ViewInject(R.id.tv_grey2)
    private TextView tvGrey2;
    @ViewInject(R.id.tv_grey3)
    private TextView tvGrey3;
    @ViewInject(R.id.tv_grey4)
    private TextView tvGrey4;
    @ViewInject(R.id.tv_green1)
    private TextView tvGreen1;
    @ViewInject(R.id.tv_green2)
    private TextView tvGreen2;
    @ViewInject(R.id.tv_green3)
    private TextView tvGreen3;
    @ViewInject(R.id.tv_icon1)
    private TextView tvIcon1;
    @ViewInject(R.id.tv_icon2)
    private TextView tvIcon2;
    @ViewInject(R.id.tv_icon3)
    private TextView tvIcon3;
    @ViewInject(R.id.tv_icon4)
    private TextView tvIcon4;
    private TitleViewHelper titleViewHelper;

    // gv...
    private SupportScrollConflictGridView gvQianfeng, gvZhongchang, gvHouwei, gvMenjiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getExtras().getString(Constant.KEY_STR);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle1NomarlCenterTitle(R.drawable.ic_round_return, name, R.drawable.sel_attention_star, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_team_detail);
    }

    @Override
    protected void ui() {
        View header = LayoutInflater.from(this).inflate(R.layout.header_dataku_team, null);
        gvQianfeng = (SupportScrollConflictGridView) header.findViewById(R.id.gv_qianfeng);
        gvZhongchang = (SupportScrollConflictGridView) header.findViewById(R.id.gv_zhongchang);
        gvHouwei = (SupportScrollConflictGridView) header.findViewById(R.id.gv_houwei);
        gvMenjiang = (SupportScrollConflictGridView) header.findViewById(R.id.gv_menjiang);
        listView.addHeaderView(header, null, false);
        displayStyleChange();
        getDetailById();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new TouchAdapter(activity, data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                boolean useCache = false;
                if (type == 1) useCache = true;
                Map<String, String> post = new HashMap<String, String>();
                post.put("count", count + "");
                post.put("tagid", DataKuListFragment.ACTION_TEAM + "+" + id);
                post.put("max", max + "");
                post.put("pageindex", page + "");
                RequestHelper.post(Constant.URL_NEWS_GETDATASNEWS, post, TouchListRespBean.class, true, useCache, listener);

            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }

    private void getDetailById() {
        User loginUser = MyApplication.getInstance().getLoginUser();
        String sid = "0";
        if (loginUser != null) sid = loginUser.getSid();
        Map<String, String> post = DataUtils.getSidPostMap(sid);
        post.put("id", id);
        detailTask = RequestHelper.post(Constant.URL_GET_TEAM_BY_ID, post, TeamDetailRespBean.class, true, true, this);
    }

    private void displayStyleChange() {
        tvGreen1.setText("所属联赛");
        tvGreen2.setText("球队头牌");
        tvGreen3.setText("主教练");
        tvIcon1.setText("城市");
        Drawable cityDrawable = getResources().getDrawable(R.drawable.ic_grey_city);
        cityDrawable.setBounds(0, 0, cityDrawable.getMinimumWidth(), cityDrawable.getMinimumHeight());
        tvIcon1.setCompoundDrawables(null, cityDrawable, null, null);
        tvIcon2.setText("主场");
        Drawable ballpartDrawble = getResources().getDrawable(R.drawable.ic_grey_ballpart);
        ballpartDrawble.setBounds(0, 0, ballpartDrawble.getMinimumWidth(), ballpartDrawble.getMinimumHeight());
        tvIcon2.setCompoundDrawables(null, ballpartDrawble, null, null);
        tvIcon3.setText("创建时间");
        tvIcon4.setText("昵称");
        Drawable nickDrawble = getResources().getDrawable(R.drawable.ic_grey_nick);
        nickDrawble.setBounds(0, 0, nickDrawble.getMinimumWidth(), nickDrawble.getMinimumHeight());
        tvIcon4.setCompoundDrawables(null, nickDrawble, null, null);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(detailTask)) {
            TeamDetailRespBean bean = (TeamDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(TeamDetailBean list) {
        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(list.getLogo()), logo);
        tvRank.setText(String.format(getString(R.string.str_dataku_detail_club_rank), list.getWorldrank()));
        tvWhite1.setText(list.getBelonggame());
        tvWhite2.setText(list.getTopathlete());
        tvWhite3.setText(list.getCoach());
        tvGrey1.setText(list.getCity());
        tvGrey2.setText(list.getHomecourt());
        tvGrey3.setText(DateUtils.formatDate(new Date(list.getCreatetime() * 1000), DateUtils.DATE_TIME_yyyy_MM_dd));
        tvGrey4.setText(list.getNickname());
        TeamHeaderAdapter adapter1 = new TeamHeaderAdapter(this, list.getPos1());
        gvQianfeng.setAdapter(adapter1);
        TeamHeaderAdapter adapter2 = new TeamHeaderAdapter(this, list.getPos2());
        gvZhongchang.setAdapter(adapter2);
        TeamHeaderAdapter adapter3 = new TeamHeaderAdapter(this, list.getPos3());
        gvHouwei.setAdapter(adapter3);
        TeamHeaderAdapter adapter4 = new TeamHeaderAdapter(this, list.getPos4());
        gvMenjiang.setAdapter(adapter4);
        CommonHelper.dataDetailAttentionControl(this, titleViewHelper.getTitle1Right(), list.getInterest(), 3, id);
    }
}
