package com.yuedong.football_mad.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CompetitionDetailBean;
import com.yuedong.football_mad.model.bean.CompetitionDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据酷赛事详情
 */
public class CompetitionDetailActivity extends BaseActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getExtras().getString(Constant.KEY_STR);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, name, R.drawable.sel_attention_star, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_competition_detail);
    }

    @Override
    protected void ui() {
        View header = LayoutInflater.from(this).inflate(R.layout.listview, null);
        listView.addHeaderView(header,null,false);
        scoreListview = (SupportScrollConflictListView) header.findViewById(R.id.spListView);
        getCompetitionInfo();
    }


    /**
     * 获取详细信息
     */
    private void getCompetitionInfo() {
        Map<String,String> post = new HashMap<>();
        post.put("id",id);
        detailTask =  RequestHelper.post(Constant.URL_GETGAMEINFO_BYID,post, CompetitionDetailRespBean.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            CompetitionDetailRespBean bean = (CompetitionDetailRespBean) data;
            renderUi(bean.getData().getList());
            refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
                @Override
                public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                    return new TouchAdapter(activity,data);
                }

                @Override
                public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                    Map<String,String> post = new HashMap<String, String>();
                    post.put("count",count+"");
                    post.put("hometeamid",count+"");
                    post.put("guestteamid",count+"");
                    post.put("max",max+"");
                    post.put("pageindex",page+"");
                }

                @Override
                public void networkSucceed(ListResponse<TouchBean> list) {

                }

                @Override
                public void contentIsEmpty() {

                }
            });
        }
    }

    private void renderUi(CompetitionDetailBean bean) {
        tvWhite1.setText(bean.getCountry());
        tvWhite2.setText(bean.getTopteam());
        tvWhite3.setText(bean.getTopathlete());
        DisplayImageByVolleyUtils.loadImage(logo, UrlHelper.checkUrl(bean.getLogo()));
        tvRank.setText(String.format(getString(R.string.str_dataku_detail_rank), bean.getWorldrank()));
        tvGrey1.setText(bean.getWorldcup());
        tvGrey2.setText(bean.getChampion());
        tvGrey3.setText(DateUtils.formatDate(new Date(bean.getCreatetime() * 1000), DateUtils.DATE_TIME_yyyy_MM_dd));
        tvGrey4.setText(bean.getShooter());
    }
}
