package com.yuedong.football_mad.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CompetitionListAdapter;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.DataKuListRespBean;
import com.yuedong.football_mad.model.bean.FinalCompetitionBean;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchBallActivity extends BaseActivity {
    private String gameTask;
    @ViewInject(R.id.listview)
    private ListView listView;
    private String competitionId;// 赛事id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_watch_ball);
    }

    @Override
    protected void ui() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_game_list, null);
        listView.addHeaderView(headerView, null, false);
        getCompetition();

    }

    // 获取赛事
    private void getCompetition() {
        Map<String,String> post =  new HashMap<>();
        post.put("count", "50");
        gameTask = RequestHelper.post(Constant.URL_GAMELIST, null, DataKuListRespBean.class, false, false, this);

    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(gameTask)) {
            DataKuListRespBean bean  = (DataKuListRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(List<FinalCompetitionBean> list) {
        if(list != null && !list.isEmpty())
            competitionId = list.get(0).getId();
        CompetitionListAdapter adapter = new CompetitionListAdapter(this,list);
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.btn_left})
    protected void clickEvent(){

    }

}
