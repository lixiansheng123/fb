package com.yuedong.football_mad.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.OtherDetailBean;
import com.yuedong.football_mad.model.bean.OtherDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherDetailActivity extends BaseActivity {
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
                post.put("tagid", DataKuListFragment.ACTION_OTHER + "+" + id);
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
        detailTask = RequestHelper.post(Constant.URL_GET_OTHE_BY_ID, post, OtherDetailRespBean.class, true, true, this);
    }
    private void displayStyleChange() {
        ViewUtils.hideLayouts(tvGreen1,tvGreen2,tvGreen3,tvIcon1,tvIcon2,tvIcon3,tvIcon4);
    }
    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            OtherDetailRespBean bean = (OtherDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(OtherDetailBean list) {
        DisplayImageByVolleyUtils.loadBallDefault(UrlHelper.checkUrl(list.getLogo()), logo);
        tvWhite1.setText(list.getAttr1());
        tvWhite2.setText(list.getAttr2());
        tvWhite3.setText(list.getAttr3());
        tvGrey1.setText(list.getAttr4());
        tvGrey2.setText(list.getAttr5());
        tvGrey3.setText(list.getAttr6());
        tvGrey4.setText(list.getAttr7());
        tvRank.setText(list.getAttr8());
    }
}
