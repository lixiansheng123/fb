package com.yuedong.football_mad.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.utils.L;

/**
 * @author 俊鹏 on 2016/6/16
 */
public class LoadDialog extends Dialog {
    public TextView mMsg;
    public ImageView mLoaderPic;

    public LoadDialog(Context context) {
        super(context, R.style.style_dialog2);
        LayoutInflater layoutinflater = LayoutInflater.from(context);
        View view = layoutinflater.inflate(R.layout.dialog_load, null);
        mMsg= (TextView) view.findViewById(R.id.id_dialog_msg);
        mLoaderPic = (ImageView) view.findViewById(R.id.id_load_pic);
        Integer[] phoneWh = MyApplication.getInstance().getPhoneWh();
        int windowW1_3 = phoneWh[0] / 3;
        ViewGroup.LayoutParams rootParams = view.getLayoutParams();
        if(rootParams == null)
            rootParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootParams.width = rootParams.height = windowW1_3;
        ViewGroup.LayoutParams picLayouParams = mLoaderPic.getLayoutParams();
        picLayouParams.width = picLayouParams.height = windowW1_3 * 5 / 12;
        setContentView(view);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                L.d("onDismiss loaddialog");
                RequestHelper.cancleAll();
            }
        });
    }
}
