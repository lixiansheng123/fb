package com.yuedong.football_mad.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.PlayerDetailBean;
import com.yuedong.football_mad.model.bean.PlayerDetailRespBean;
import com.yuedong.football_mad.model.bean.TouchBean;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.DateUtils;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.view.SupportScrollConflictListView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlayerDetailActivity extends BaseActivity {
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
    }

    private void getDetailById() {
        Map<String,String> post = new HashMap<>();
        post.put("id", id);
      detailTask =  RequestHelper.post(Constant.URL_GET_ATHLETE_BY_ID,post, PlayerDetailRespBean.class,true,true,this);

    }

    private void displayStyleChange() {
        tvGreen1.setText("第一国籍");
        tvGreen2.setText("所属联盟");
        tvGreen3.setText("所在球队");
        tvIcon1.setText("号码");
        Drawable cityDrawable = getResources().getDrawable(R.drawable.ic_grey_clothing);
        cityDrawable.setBounds(0, 0, cityDrawable.getMinimumWidth(), cityDrawable.getMinimumHeight());
        tvIcon1.setCompoundDrawables(null, cityDrawable, null, null);
        tvIcon2.setText("位置");
        Drawable ballpartDrawble = getResources().getDrawable(R.drawable.ic_grey_positon);
        ballpartDrawble.setBounds(0, 0, ballpartDrawble.getMinimumWidth(), ballpartDrawble.getMinimumHeight());
        tvIcon2.setCompoundDrawables(null, ballpartDrawble, null, null);
        tvIcon3.setText("出生日期");
        tvIcon4.setText("昵称");
        Drawable nickDrawble = getResources().getDrawable(R.drawable.ic_grey_nick);
        nickDrawble.setBounds(0, 0, nickDrawble.getMinimumWidth(), nickDrawble.getMinimumHeight());
        tvIcon4.setCompoundDrawables(null, nickDrawble, null, null);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            PlayerDetailRespBean bean = (PlayerDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(PlayerDetailBean list) {
        DisplayImageByVolleyUtils.loadImage(logo, UrlHelper.checkUrl(list.getLogo()));
        tvRank.setText(String.format(getString(R.string.str_dataku_detail_price), list.getPrice()));
        tvWhite1.setText(list.getCountry());
        tvWhite2.setText(list.getGame());
        tvWhite3.setText(list.getTeam());
        tvGrey1.setText(list.getNumber());
        tvGrey2.setText(CommonHelper.getTextByBallPos(list.getPos()));
        tvGrey3.setText(list.getBirthday());
        tvGrey4.setText(list.getNickname());
    }
}
