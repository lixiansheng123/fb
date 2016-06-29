package com.yuedong.football_mad.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.ViewUtils;


/**
 * 评论互动窗口
 * @author 俊鹏 on 2016/6/29
 */
public class CommonInteractPop extends PopupWindow implements View.OnClickListener {
    private Context context;
    private View content;
    private OnCommentInteractCallback callback;
//    private Animation inAnimation,outAnimation;
    private View main;
    private Handler delayHandler;
    public CommonInteractPop(Context context){
        this.context = context;
        content = LayoutInflater.from(context).inflate(R.layout.pop_comment_interact,null);
        main = content.findViewById(R.id.ll_main);
        setContentView(content);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setOutsideTouchable(true);
        // 设置动画样式
        setAnimationStyle(R.style.AnimFace);
//        inAnimation = AnimationUtils.loadAnimation(context,R.anim.anim_go_up);
//        outAnimation = AnimationUtils.loadAnimation(context,R.anim.anim_decline);
//        ViewUtils.hideLayout(main);
        delayHandler = new Handler();
        initEvent();

    }

    public void setOnCommentInteractCallback(OnCommentInteractCallback callback){
        this.callback = callback;
    }

    private void initEvent() {
        content.findViewById(R.id.iv_replay).setOnClickListener(this);
        content.findViewById(R.id.iv_collect).setOnClickListener(this);
        content.findViewById(R.id.iv_copy).setOnClickListener(this);
        content.findViewById(R.id.iv_reports).setOnClickListener(this);
        content.findViewById(R.id.iv_weixin).setOnClickListener(this);
        content.findViewById(R.id.iv_weibo).setOnClickListener(this);
        content.findViewById(R.id.iv_friend).setOnClickListener(this);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
//            delayHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    main.startAnimation(inAnimation);
//                }
//            },350);
//            inAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    L.d("inAnimation-onAnimationEnd");
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        } else {
//            main.startAnimation(outAnimation);
//            outAnimation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    dismiss();
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
        }
    }

    @Override
    public void onClick(View v) {
        showPopupWindow(null);
        if(callback == null)return;
        switch (v.getId()){
            case R.id.iv_collect:
                callback.onCollect();
                break;
            case R.id.iv_copy:
                callback.onCopy();
                break;
            case R.id.iv_reports:
                callback.onReport();
                break;
            case R.id.iv_weixin:
                callback.onWeixin();
                break;
            case R.id.iv_weibo:
                callback.onWeibo();
                break;
            case R.id.iv_friend:
                callback.onFriend();
                break;

            case R.id.iv_replay:
                callback.onReply();
                break;
        }
    }

    public interface OnCommentInteractCallback{
        void onReply();
        void onCopy();
        void onWeixin();
        void onWeibo();
        void onCollect();
        void onFriend();
        void onReport();
    }
}
