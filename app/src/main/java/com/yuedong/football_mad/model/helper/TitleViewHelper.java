package com.yuedong.football_mad.model.helper;

import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    //---------title3
    private View title3;
    private ImageView title3Left;
    private TextView title3Title;
    private ImageView ivTitle3Right;
    private LinearLayout title3Right;
    private FragmentActivity context;



    public TitleViewHelper(FragmentActivity context) {
        this.context = context;
    }

    public View getTitle1NomarlCenterIcon(@DrawableRes int leftIcon, @DrawableRes int centerIcon, @DrawableRes int rightIcon, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr) {
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


    public View getTitle1NomarlCenterTitle(@DrawableRes int leftIcon, String title, @DrawableRes int rightIcon, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr) {
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
    public ImageView getTitle1Left(){return title1Left;}
    public ImageView getTitle1Right(){
        return  title1Right;
    }

    public ImageView getTitle3Right(){
        return ivTitle3Right;
    }

    public View getTitle1NonentityCenter(@DrawableRes int leftIcon, @DrawableRes int rightIcon, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr) {
        initTitle1View();
        title1Left.setImageResource(leftIcon);
        title1Right.setImageResource(rightIcon);
        titleCenter.setVisibility(View.GONE);
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


    public View getTitle1NoRight(@DrawableRes int leftIcon, @DrawableRes int centerIcon, final View.OnClickListener leftListenr) {
        initTitle1View();
        title1Left.setImageResource(leftIcon);
        title1Right.setVisibility(View.GONE);
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
        return titile1;
    }

    public View getTitle1NoRightCenterTitle(@DrawableRes int leftIcon,  String title, final View.OnClickListener leftListenr) {
        initTitle1View();
        title1Left.setImageResource(leftIcon);
        titleTvTitle.setText(title);
        title1Right.setVisibility(View.GONE);
        titleCenter.setVisibility(View.GONE);
        titleTvTitle.setVisibility(View.VISIBLE);
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
        return titile1;
    }


    public View getTitle3(@DrawableRes int leftIcon,String title, final View.OnClickListener leftListenr, final View.OnClickListener rightListenr){
        initTitle3View();
        title3Left.setImageResource(leftIcon);
        title3Title.setText(title);
        title3Left.setOnClickListener(new View.OnClickListener() {
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
            title3Right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightListenr.onClick(v);
                }
            });
        }
        return title3;
    }

    private void initTitle3View(){
        title3 = LayoutInflater.from(App.getInstance().getAppContext()).inflate(R.layout.title3,null);
        title3Left = (ImageView) title3.findViewById(R.id.title_btn_left);
        title3Title = (TextView) title3.findViewById(R.id.tv_title);
        title3Right = (LinearLayout) title3.findViewById(R.id.title_btn_right);
        ivTitle3Right = (ImageView) title3.findViewById(R.id.iv_title3_right);
    }


    private void initTitle1View() {
        titile1 = LayoutInflater.from(App.getInstance().getAppContext()).inflate(R.layout.title_1, null);
        title1Left = (ImageView) titile1.findViewById(R.id.title_btn_left);
        title1Right = (ImageView) titile1.findViewById(R.id.title_btn_right);
        titleCenter = (RelativeLayout) titile1.findViewById(R.id.title_center);
        titleTvTitle = (TextView) titile1.findViewById(R.id.tv_title);
    }

}
