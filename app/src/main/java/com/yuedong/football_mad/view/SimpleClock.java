package com.yuedong.football_mad.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.utils.DimenUtils;

import java.util.Calendar;
import java.util.logging.Handler;

/**
 * 简单时钟
 * @author 俊鹏 on 2016/6/27
 */
public class SimpleClock extends LinearLayout{
    private TextView tvDay,tvTime;
    private android.os.Handler clockHandler;
    public SimpleClock(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        clockHandler = new android.os.Handler();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(R.drawable.ic_white_clock);
        tvDay = new TextView(getContext());
        tvTime = new TextView(getContext());
        textViewStyle(tvDay);
        textViewStyle(tvTime);
        addView(tvDay);
        addView(tvTime);
        clockUi();
    }

    private void textViewStyle(TextView textView){
        textView.setTextSize(9);
        textView.setTextColor(Color.parseColor("#EEEEEE"));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.leftMargin = DimenUtils.dip2px(getContext(),37);
        textView.setLayoutParams(llp);
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
        if (week == 1) {
            weekStr = "周末";
        } else if (week == 2) {
            weekStr = "周一";
        } else if (week == 3) {
            weekStr = "周二";
        } else if (week == 4) {
            weekStr = "周三";
        } else if (week == 5) {
            weekStr = "周四";
        } else if (week == 6) {
            weekStr = "周五";
        } else if (week == 7) {
            weekStr = "周六";
        }
        tvDay.setText(year + "." + month + "." + day);
        tvTime.setText(weekStr + hour + ":" + minute);
        clockHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clockUi();
            }
        }, 60000);
    }

}
