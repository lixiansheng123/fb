package com.yuedong.football_mad.model.helper;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;

/**
 * @author 俊鹏 on 2016/6/12
 */
public class TitleViewHelper {
    private View titile1;
    private ImageView title1Left;
    private ImageView title1Right;
    private RelativeLayout titleCenter;
    private TextView titleTvTitle;
    private FragmentActivity context;

    public TitleViewHelper(FragmentActivity context) {
        this.context = context;
    }

    public View getNomarlCenterIcon(@DrawableRes int leftIcon, @DrawableRes int centerIcon, @DrawableRes int rightIcon, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr) {
        initTitle1View();
        title1Left.requestLayout();
        title1Left.setImageResource(leftIcon);
        title1Right.setImageResource(rightIcon);
        titleCenter.setBackgroundResource(centerIcon);
        title1Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListenr != null) {
                    leftListenr.onClick(v);
                } else {
                    LaunchWithExitUtils.back(context);
                }
            }
        });

        if (rightListenr != null) {
            title1Right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightListenr.onClick(v);
                }
            });
        }
        return titile1;
    }


    public View getNomarlCenterTitle(@DrawableRes int leftIcon, String title, @DrawableRes int rightIcon, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr) {
        initTitle1View();
        title1Left.setImageResource(leftIcon);
        title1Right.setImageResource(rightIcon);
        titleTvTitle.setVisibility(View.VISIBLE);
        titleTvTitle.setText(title);
        titleCenter.setBackgroundResource(android.R.color.transparent);
        title1Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftListenr != null) {
                    leftListenr.onClick(v);
                } else {
                    LaunchWithExitUtils.back(context);
                }
            }
        });
        if (rightListenr != null) {
            title1Right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightListenr.onClick(v);
                }
            });
        }
        return titile1;
    }


    private void initTitle1View() {
        titile1 = LayoutInflater.from(App.getInstance().getAppContext()).inflate(R.layout.title_1, null);
        title1Left = (ImageView) titile1.findViewById(R.id.title_btn_left);
        title1Right = (ImageView) titile1.findViewById(R.id.title_btn_right);
        titleCenter = (RelativeLayout) titile1.findViewById(R.id.title_center);
        titleTvTitle = (TextView) titile1.findViewById(R.id.tv_title);
    }
}
