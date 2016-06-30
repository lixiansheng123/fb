package com.yuedong.football_mad.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.L;

/**
 * @author 俊鹏 on 2016/6/30
 */
public class CriticismPop extends PopupWindow implements View.OnClickListener {
    private Context context;
    private View content;
    private OnCriticismCallback callback;
    private ImageView[] ivs = new ImageView[3];
    private int selectItem = 0;
    public void setOnCriticismCallback(OnCriticismCallback callback){
        this.callback = callback;
    }

    public CriticismPop(Context context){
        this.context = context;
        content = LayoutInflater.from(context).inflate(R.layout.layout_criticism,null);
        setContentView(content);
        setWidth(DimenUtils.dip2px(context,150));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setOutsideTouchable(true);
        // 设置动画样式
        setAnimationStyle(R.style.AnimFace);

        initView();
    }

    private void initView() {
        content.findViewById(R.id.id_select_cancle).setOnClickListener(this);
        content.findViewById(R.id.id_select_confirm).setOnClickListener(this);
        content.findViewById(R.id.item1).setOnClickListener(this);
        content.findViewById(R.id.item2).setOnClickListener(this);
        content.findViewById(R.id.item3).setOnClickListener(this);
        ivs[0] = (ImageView) content.findViewById(R.id.iv1);
        ivs[1] = (ImageView) content.findViewById(R.id.iv2);
        ivs[2] = (ImageView) content.findViewById(R.id.iv3);
        styleByItem();
    }

    public void showPopToTop(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
//        L.d("x:"+location[0]+"y:"+location[1]);
        this.showAtLocation(view, Gravity.NO_GRAVITY,location[0],location[1]-DimenUtils.dip2px(context,143)- 10);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_select_cancle:
                dismiss();
                break;
            case R.id.id_select_confirm:
                dismiss();
                if(callback!=null)callback.callback(selectItem);
                break;

            case R.id.item1:
                selectItem =0;
                styleByItem();
                break;

            case R.id.item2:
                selectItem = 1;
                styleByItem();
                break;

            case R.id.item3:
                selectItem =2;
                styleByItem();
                break;
        }
    }

    public interface OnCriticismCallback{
        void callback(int type);
    }

    private void styleByItem(){
        for(int i = 0;i < ivs.length; i++){
            ImageView iv = ivs[i];
            if(selectItem == i)
                iv.setSelected(true);
            else
                iv.setSelected(false);

        }
    }
}
