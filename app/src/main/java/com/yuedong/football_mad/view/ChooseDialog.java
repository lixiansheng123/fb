package com.yuedong.football_mad.view;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.CommonCallback;

/**
 * @author 俊鹏 on 2016/6/16
 */
public class ChooseDialog extends Dialog {
    protected CommonCallback callback;

    public ChooseDialog(Context context){
        super(context, R.style.style_dialog);
    }
    public void setCallback(CommonCallback callback){
        this.callback = callback;
    }
    @Override
    public void show() {
        if (isShowing()) return;
        super.show();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (MyApplication.getInstance().getPhoneWh()[0]); // 设置宽度
        this.getWindow().setAttributes(lp);
    }
}
