package com.yuedong.football_mad.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.GameDetailBean;
import com.yuedong.football_mad.model.bean.GameDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.bean.TouchListRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_grey1)
    private TextView tvGrey1;
    @ViewInject(R.id.tv_grey2)
    private TextView tvGrey2;
    @ViewInject(R.id.tv_grey3)
    private TextView tvGrey3;
    @ViewInject(R.id.tv_grey4)
    private TextView tvGrey4;
    @ViewInject(R.id.tv_icon1)
    private TextView tvIcon1;
    @ViewInject(R.id.tv_icon2)
    private TextView tvIcon2;
    @ViewInject(R.id.tv_icon3)
    private TextView tvIcon3;
    @ViewInject(R.id.tv_icon4)
    private TextView tvIcon4;
    @ViewInject(R.id.rb_star)
    private RatingBar ratingBar;
    @ViewInject(R.id.ll_attention)
    private View llAttention;
    @ViewInject(R.id.iv_attention)
    private ImageView ivAttention;
    @ViewInject(R.id.iv_home_logo)
    private NetworkImageView ivHomeLogo;
    @ViewInject(R.id.iv_guest_logo)
    private NetworkImageView ivGuestLogo;
    @ViewInject(R.id.iv_logo)
    private NetworkImageView ivLogo;
    @ViewInject(R.id.tv_home_name)
    private TextView tvHomeName;
    @ViewInject(R.id.tv_guest_name)
    private TextView tvGuestName;
    @ViewInject(R.id.tv_game_day)
    private TextView tvGameDay;
    @ViewInject(R.id.tv_game_time)
    private TextView tvGameTime;
    @ViewInject(R.id.tv_score)
    private TextView tvScore;
    @ViewInject(R.id.tv_channel)
    private TextView tvChannel;
    @ViewInject(R.id.tv_game_after_remark)
    private TextView tvGameAfter;
    @ViewInject(R.id.tv_game_before_remark)
    private TextView tvGameBrfore;
    @ViewInject(R.id.ll_before)
    private View llBrfore;
    @ViewInject(R.id.ll_after)
    private View llAfter;
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    private RefreshProxy<TouchBean> refreshProxy = new RefreshProxy<>();
    private String id;
    private boolean isAttention;// 是否关注了
    private String infoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_game_detail);
        Bundle extras = getIntent().getExtras();
        id =  extras.getString(Constant.KEY_ID);
        isAttention = extras.getBoolean(Constant.KEY_BOOL);
    }

    @Override
    protected void ui() {
        // 防止没有网络点击
        llBrfore.setClickable(false);
        llAfter.setClickable(false);
        topStyleControl();
        getGameInfoById();
    }

    private void getGameInfoById() {
        Map<String,String> post = new HashMap<>();
        post.put("id", id);
        infoTask = RequestHelper.post(Constant.URL_GET_GAME_INFO_BYID,post,GameDetailRespBean.class,true,true,this);

    }

    private void topStyleControl() {
        if(isAttention){
            ivAttention.setImageResource(R.drawable.ic_game_attention);
//            llAttention.setClickable(false);
        }else{
            ivAttention.setImageResource(R.drawable.ic_game_unattention);
//            llAttention.setClickable(true);
        }
        ViewUtils.hideLayout(tvGrey4);
        ViewUtils.showLayout(ratingBar);
        tvIcon1.setText("主场");
        Drawable homeDrawable = getResources().getDrawable(R.drawable.ic_grey_home);
        homeDrawable.setBounds(0, 0, homeDrawable.getMinimumWidth(), homeDrawable.getMinimumHeight());
        tvIcon1.setCompoundDrawables(null, homeDrawable, null, null);
        tvIcon2.setText("主裁");
        Drawable judgeDrawable = getResources().getDrawable(R.drawable.ic_grey_judge);
        judgeDrawable.setBounds(0, 0, judgeDrawable.getMinimumWidth(), judgeDrawable.getMinimumHeight());
        tvIcon2.setCompoundDrawables(null, judgeDrawable, null, null);
        tvIcon3.setText("天气");
        Drawable weatherDrawable = getResources().getDrawable(R.drawable.ic_grey_weather);
        weatherDrawable.setBounds(0, 0, weatherDrawable.getMinimumWidth(), weatherDrawable.getMinimumHeight());
        tvIcon3.setCompoundDrawables(null, weatherDrawable, null, null);
        tvIcon4.setText("星级");
        Drawable starDrawable = getResources().getDrawable(R.drawable.ic_grey_double_star);
        starDrawable.setBounds(0, 0, starDrawable.getMinimumWidth(), starDrawable.getMinimumHeight());
        tvIcon4.setCompoundDrawables(null, starDrawable, null, null);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(infoTask)){
            GameDetailRespBean bean = (GameDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(GameDetailBean list) {
        DisplayImageByVolleyUtils.loadImage(ivHomeLogo, UrlHelper.checkUrl(list.getHometeamlogo()));
        DisplayImageByVolleyUtils.loadImage(ivGuestLogo,UrlHelper.checkUrl(list.getGuestteamlogo()));
        DisplayImageByVolleyUtils.loadImage(ivLogo, UrlHelper.checkUrl(list.getGamemark()));
        tvGameDay.setText(list.getContesttime());
        tvGameTime.setText(list.getTime());
        String score = list.getScore();
        if(TextUtils.isEmpty(score))score = "---";
        tvScore.setText(score);
        tvChannel.setText("直播："+list.getLive());
        tvHomeName.setText(list.getHometeam());
        tvGuestName.setText(list.getGuestteam());
        tvGrey1.setText(list.getHomecourt());
        tvGrey2.setText(list.getReferee());
        tvGrey3.setText(list.getWeather());
        ratingBar.setRating(list.getStar());
        // 评述ui控制
        String fullDate = list.getContesttime()+" "+list.getTime();
        Date date = DateUtils.parseString(fullDate, new SimpleDateFormat(DateUtils.DATE_TIME_yyyy_MM_dd_HH_mm));
        if(System.currentTimeMillis() >= date.getTime()){
            // 开始了
            llBrfore.setClickable(false);
            llAfter.setClickable(true);
            tvGameAfter.setTextColor(Color.parseColor("#4B8D7F"));
            Drawable d = getResources().getDrawable(R.drawable.ic_green_football_pingshu2);
            d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
            tvGameAfter.setCompoundDrawables(null, null, d, null);

            tvGameBrfore.setTextColor(Color.parseColor("#5D5D5D"));
            Drawable d2 = getResources().getDrawable(R.drawable.ic_grey_football_pingshu2);
            d2.setBounds(0, 0, d2.getMinimumWidth(), d2.getMinimumHeight());
            tvGameBrfore.setCompoundDrawables(d2,null,null,null);
        }else{
            // 未开始
            llAfter.setClickable(false);
            llBrfore.setClickable(true);
        }


        getList(list.getHometeamid(),list.getGuestteamid());
    }

    private void getList(final String hometeamid, final String guestteamid) {
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<TouchBean>() {
            @Override
            public BaseAdapter<TouchBean> getAdapter(List<TouchBean> data) {
                return new TouchAdapter(activity,data);
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                Map<String,String> post = new HashMap<String, String>();
                post.put("max",max+"");
                post.put("pageindex",page+"");
                post.put("count",count+"");
                post.put("hometeamid",hometeamid);
                post.put("guestteamid",guestteamid);
                RequestHelper.post(Constant.URL_GET_CONTEST_NEWS,post, TouchListRespBean.class,false,false,listener);
            }

            @Override
            public void networkSucceed(ListResponse<TouchBean> list) {

            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }

    @OnClick({R.id.ll_attention,R.id.btn_left,R.id.ll_before,R.id.ll_after})
    public void clickEvent(View view){
        switch (view.getId()){
            case R.id.ll_attention:
                if(!isAttention)
                if(DataUtils.attentionGame(id)){
                    // 关注变更
                    MyApplication.getInstance().attentionGameChange = true;
                    ivAttention.setImageResource(R.drawable.ic_game_attention);
                    T.showShort(activity,"关注成功");
                }else{
                    DataUtils.cancelAttentionGame(id);
                    MyApplication.getInstance().attentionGameChange = true;
                    ivAttention.setImageResource(R.drawable.ic_game_unattention);
                    T.showShort(activity,"取消关注成功");
                }
                break;
            case R.id.btn_left:
                back();
                break;

            case R.id.ll_after:
                T.showShort(activity,"after...");
                break;
            case R.id.ll_before:
                T.showShort(activity,"before...");
                break;
        }

    }

}
