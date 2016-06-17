package com.yuedong.football_mad.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.utils.DimenUtils;

/**
 * @author 俊鹏 on 2016/6/17
 */
public class DoubleTabView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private TextView item1,item2;
    private ITagClickListener iTagClickListener;
    private String text1,text2;

    public DoubleTabView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DoubleTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttributes(attrs);
        init();
    }


    private void getAttributes(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DoubleTabView);
        text1 = typedArray.getString(R.styleable.DoubleTabView_text1);
        text2 = typedArray.getString(R.styleable.DoubleTabView_text2);
        typedArray.recycle();
    }


    public void setITagClickListener(ITagClickListener iTagClickListener){
        this.iTagClickListener = iTagClickListener;
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_double_view,null);
        item1 = (TextView) view.findViewById(R.id.item1);
        item2 = (TextView) view.findViewById(R.id.item2);
        item1.setText(text1);
        item2.setText(text2);
        addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        clickStyle(item1);
    }

    private void clickStyle(View clickView){
        item1.setTextColor(Color.parseColor("#666666"));
        item1.setTextSize(12);
        item2.setTextColor(Color.parseColor("#666666"));
        item2.setTextSize(12);
        ((TextView)clickView).setTextColor(Color.parseColor("#4B8D7F"));
        ((TextView)clickView).setTextSize(15);
    }

    @Override
    public void onClick(View v) {
        clickStyle(v);
        if(iTagClickListener!=null)
            iTagClickListener.onClick(v);
    }

    public interface ITagClickListener{
        void onClick(View view);
    }
}
