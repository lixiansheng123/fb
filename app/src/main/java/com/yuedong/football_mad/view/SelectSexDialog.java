package com.yuedong.football_mad.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.CommonCallback;
import com.yuedong.football_mad.model.helper.CommonHelper;

/**
 * @author 俊鹏 on 2016/6/16
 */
public class SelectSexDialog extends ChooseDialog implements View.OnClickListener {
    private View mContentView;
    private ImageView ivMan,ivWoman,ivSecrecy;


    public SelectSexDialog(Context context){
        super(context);
        mContentView = getLayoutInflater().from(context).inflate(R.layout.dialog_choose_sex, null);
        setContentView(mContentView);

        ivMan = (ImageView) mContentView.findViewById(R.id.iv_man);
        ivWoman = (ImageView) mContentView.findViewById(R.id.iv_woman);
        ivSecrecy = (ImageView) mContentView.findViewById(R.id.iv_secrecy);
        mContentView.findViewById(R.id.view_gap).setOnClickListener(this);
        ivMan.setOnClickListener(this);
        ivWoman.setOnClickListener(this);
        ivSecrecy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){
            case R.id.iv_man:callback(1, CommonHelper.getTextBySex(1));break;
            case R.id.iv_woman: callback(2,CommonHelper.getTextBySex(2));break;
            case R.id.iv_secrecy:callback(0, CommonHelper.getTextBySex(0));break;
        }
    }
    private void callback(int sex,String sexText){
        if(callback !=null)
            callback.callback(sex,sexText);
    }

}
