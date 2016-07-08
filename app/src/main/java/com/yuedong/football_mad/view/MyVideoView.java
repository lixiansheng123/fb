package com.yuedong.football_mad.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author 俊鹏 on 2016/7/8
 */
public class MyVideoView extends VideoView {

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
