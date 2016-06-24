package com.yuedong.football_mad.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.Calendar;
import java.util.Date;

public class WatchBallActivity extends BaseActivity {
    @ViewInject(R.id.tv_day)
    private TextView tvDay;
    @ViewInject(R.id.tv_time)
    private TextView tvTime;
    private Handler clockHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_watch_ball);
        clockHandler = new Handler();
    }

    @Override
    protected void ui() {
        clockUi();
    }

    private void clockUi() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        if(week == 1){
            weekStr = "周末";
        }else if(week == 2){
           weekStr = "周一";
        }else if(week == 3){
            weekStr = "周二";
        }else if(week == 4){
            weekStr = "周三";
        }else if(week == 5){
            weekStr = "周四";
        }else if(week == 6){
            weekStr = "周五";
        }else if(week == 7){
            weekStr = "周六";
        }
        tvDay.setText(year+"."+month+"."+day);
        tvTime.setText(weekStr+hour+":"+minute);
        clockHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clockUi();
            }
        },60000);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
