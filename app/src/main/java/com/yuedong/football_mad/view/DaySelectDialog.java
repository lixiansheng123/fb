package com.yuedong.football_mad.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.WheelOnlyTextAdapter;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.lib_develop.utils.L;

import java.util.Calendar;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

public class DaySelectDialog extends ChooseDialog {
    private WheelView yearWv, monthWv, dayWv;
    private Context mContext;
    private String curYear;
    private String curMonth;
    private View mContentView;
    private boolean selectFinished = true;
    private List<String> daysData;
    private List<String> yearsData;
    private List<String> monthData;
    private WheelOnlyTextAdapter adapter;
    private WheelOnlyTextAdapter adapter2;
    private WheelOnlyTextAdapter adapter3;

    public DaySelectDialog(Context context) {
        super(context);
        View contentView =LayoutInflater.from(context).inflate(R.layout.dialog_day_select,null);
        this.mContext = context;
        this.mContentView = contentView;
        setContentView(contentView);
        initViews();
        initEvents();
    }

    private void initEvents() {
        yearWv.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                curYear = yearsData.get(wheel.getCurrentItem());
                View view = yearWv.getItemView(wheel.getCurrentItem());
                view.setBackgroundResource(android.R.color.black);
            }
        });
        monthWv.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                selectFinished = false;
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                curMonth = monthData.get(wheel.getCurrentItem());
                daysData = DataUtils.getDays(Integer.parseInt(curYear), Integer.parseInt(curMonth));
                adapter3.setData(daysData);
                selectFinished = true;
            }
        });
        dayWv.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {

            }
        });

        mContentView.findViewById(R.id.view_gap).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mContentView.findViewById(R.id.id_select_cancle).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mContentView.findViewById(R.id.id_select_confirm).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!selectFinished)
                    return;
                dismiss();
                // 获取wheelView3当前选中的值
                String day = daysData.get(dayWv.getCurrentItem());
                if (callback != null)
                    callback.callbackYMR(curYear, curMonth, day);
            }
        });
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        yearWv = (WheelView) mContentView.findViewById(R.id.id_wheelview_year);
        monthWv = (WheelView) mContentView.findViewById(R.id.id_wheelview_month);
        dayWv = (WheelView) mContentView.findViewById(R.id.id_wheelview_day);
        setWheelViewParams(yearWv);
        setWheelViewParams(monthWv);
        setWheelViewParams(dayWv);
        adapter = new WheelOnlyTextAdapter(mContext);
        adapter2 = new WheelOnlyTextAdapter(mContext);
        adapter3 = new WheelOnlyTextAdapter(mContext);
        yearWv.setViewAdapter(adapter);
        monthWv.setViewAdapter(adapter2);
        dayWv.setViewAdapter(adapter3);
        yearsData = DataUtils.getYears(Calendar.getInstance().get(Calendar.YEAR));
        monthData = DataUtils.getMonth();
        // 当前年
        curYear = yearsData.get(yearWv.getCurrentItem());
        // 当前月
        curMonth = monthData.get(monthWv.getCurrentItem());
        daysData = DataUtils.getDays(Integer.parseInt(curYear), Integer.parseInt(curMonth));
        adapter.setData(yearsData);
        adapter2.setData(monthData);
        adapter3.setData(daysData);
        yearWv.setCurrentItem(0);
        monthWv.setCurrentItem(month); //month 是从0开始的
        dayWv.setCurrentItem(day - 1);
        curYear = yearsData.get(yearWv.getCurrentItem());
        curMonth = monthData.get(monthWv.getCurrentItem());
    }


    private void setWheelViewParams(WheelView wheelView) {
        wheelView.setShadowColor(0xB2ffffff, 0xB2ffffff, 0xB2ffffff);
        wheelView.setWheelBackground(android.R.color.transparent);
        wheelView.setWheelForeground(R.color.green488B7D);
    }
}
