package com.yuedong.football_mad.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.WheelOnlyTextAdapter;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.L;

import java.util.Calendar;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

public class DaySelectPop extends PopupWindow implements View.OnClickListener {
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
    private CommonCallback callback;
    private View root;
    private int popHeight;

    public void setCallback(CommonCallback callback) {
        this.callback = callback;
    }

    private Handler handler;

    public DaySelectPop(Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_day_select2, null);
        this.mContext = context;
        this.mContentView = contentView;
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popHeight = DimenUtils.dip2px(context, 120);
        setHeight(popHeight);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setOutsideTouchable(true);
        // 设置动画样式
        setAnimationStyle(R.style.AnimFace);
        initViews();
        initEvents();
    }

    private void initEvents() {
        mContentView.findViewById(R.id.btn_ately).setOnClickListener(this);
        yearWv.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                curYear = yearsData.get(wheel.getCurrentItem());
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

    }

    private void initViews() {
        handler = new Handler(Looper.getMainLooper());
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
//        // 当前年
        curYear = yearsData.get(yearWv.getCurrentItem());
        // 当前月
        curMonth = monthData.get(monthWv.getCurrentItem());
        daysData = DataUtils.getDays(Integer.parseInt(curYear), Integer.parseInt(curMonth));
        adapter.setData(yearsData);
        adapter2.setData(monthData);
        adapter3.setData(daysData);
        yearWv.setCurrentItem(yearsData.size() - 1);
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

    public void showPop(View view) {
        if (!this.isShowing()) {
            this.showAsDropDown(view);
//            anim(0,popHeight,root,false);
        } else {
//            anim(popHeight, 0, root, true);
            dismiss();
        }

    }

   /* private void anim(int from,int to,View target,boolean dismiss){
        ValueAnimator va = ValueAnimator.ofInt(from,to);
        va.setTarget(target);
        va.setDuration(500);
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                L.d("pass by...update");
                int height = (int) animation.getAnimatedValue();
                root.getLayoutParams().height = height;
            }
        });
        if(dismiss) {
            va.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                  dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ately:
                int[] ints = curtimePositionByData();
                yearWv.setCurrentItem(yearsData.size() - 1, true);
                monthWv.setCurrentItem(ints[0], true);
                dayWv.setCurrentItem(ints[1], true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPop(null);
                        if (callback != null)
                            callback.callbackYMR(curYear, curMonth, daysData.get(dayWv.getCurrentItem()));
                    }
                }, 400);
                break;
        }
    }

    public String getSelYear() {
        return curYear;
    }

    public String getSelMonth() {
        return curMonth;
    }

    public String getSelDay() {
        return daysData.get(dayWv.getCurrentItem());
    }

    private int[] curtimePositionByData() {
        Calendar calendar = Calendar.getInstance();
        int monthd = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayStr = day + "";
        if (day < 10) dayStr = "0" + day;
        int dayPos = 0;
        for (int i = 0; i < daysData.size(); i++) {
            if (dayStr.equals(daysData.get(i))) {
                dayPos = i;
                break;
            }
        }
        return new int[]{monthd, dayPos};
    }
}
