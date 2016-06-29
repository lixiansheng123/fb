package com.yuedong.football_mad.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CountryDetailBean;
import com.yuedong.football_mad.model.bean.CountryDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryDetailActivity extends BaseActivity {
    private String id;
    private String detailTask;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getExtras().getString(Constant.KEY_STR);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, name, R.drawable.sel_attention_star, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_player_detail);
    }

    @Override
    protected void ui() {
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
                post.put("tagid", DataKuListFragment.ACTION_COUNTRY + "+" + id);
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
        Map<String, String> post = new HashMap<>();
        post.put("id", id);
        detailTask = RequestHelper.post(Constant.URL_GET_COUNTRY_BY_ID, post, CountryDetailRespBean.class, true, true, this);
    }

    private void displayStyleChange() {
        tvGreen1.setText("顶级联赛");
        tvGreen2.setText("国家头牌");
        tvGreen3.setText("国家队");
        tvIcon1.setText("首都");
        Drawable cityDrawable = getResources().getDrawable(R.drawable.ic_grey_city);
        cityDrawable.setBounds(0, 0, cityDrawable.getMinimumWidth(), cityDrawable.getMinimumHeight());
        tvIcon1.setCompoundDrawables(null, cityDrawable, null, null);
        tvIcon2.setText("语言");
        Drawable languageDrawable = getResources().getDrawable(R.drawable.ic_grey_language);
        languageDrawable.setBounds(0, 0, languageDrawable.getMinimumWidth(), languageDrawable.getMinimumHeight());
        tvIcon2.setCompoundDrawables(null, languageDrawable, null, null);
        tvIcon3.setText("创建时间");
        tvIcon4.setText("昵称");
        Drawable nickDrawble = getResources().getDrawable(R.drawable.ic_grey_nick);
        nickDrawble.setBounds(0, 0, nickDrawble.getMinimumWidth(), nickDrawble.getMinimumHeight());
        tvIcon4.setCompoundDrawables(null, nickDrawble, null, null);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            CountryDetailRespBean bean  = (CountryDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(CountryDetailBean list) {
        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(list.getLogo()), logo);
        tvRank.setText(String.format(getString(R.string.str_dataku_detail_fifa_rank), list.getFifarank()));
        tvWhite1.setText(list.getTopgame());
        tvWhite2.setText(list.getTopteam());
        tvWhite3.setText(list.getTopathlete());
        tvGrey1.setText(list.getCapital());
        tvGrey2.setText(list.getLan());
        tvGrey3.setText(DateUtils.formatDate(new Date(list.getCreatetime()*1000),DateUtils.DATE_TIME_yyyy_MM_dd));
        tvGrey4.setText(list.getNickname());
    }
}
