package com.yuedong.football_mad.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yuedong.football_mad.R;

/**
 * @author 俊鹏 on 2016/7/7
 */
public class SelectPhotoDialog extends ChooseDialog implements View.OnClickListener {
    private View content;
    private OnSelectPicPopCallback mCallback;
    public SelectPhotoDialog(Context context) {
        super(context);
        content = LayoutInflater.from(context).inflate(R.layout.layout_select_photo,null);
        setContentView(content);
        initViews();
    }
    public void setOnSelectPicPopCallback(OnSelectPicPopCallback callback) {
        this.mCallback = callback;
    }

    private void initViews() {
        content.findViewById(R.id.iv_take_pic).setOnClickListener(this);
        content.findViewById(R.id.iv_photoset).setOnClickListener(this);
        content.findViewById(R.id.view_gap).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){
            case R.id.iv_take_pic:
                if(mCallback!=null)mCallback.onTakePic();
                break;
            case R.id.iv_photoset:
                if(mCallback!=null)mCallback.onGetPic();
                break;
        }
    }
    public interface OnSelectPicPopCallback {

        void onTakePic();

        void onGetPic();
    }


}
