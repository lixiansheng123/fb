package com.yuedong.football_mad.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.model.helper.CommonHelper;

/**
 * @author 俊鹏 on 2016/6/16
 */
public class SelectBallPosDialog extends ChooseDialog implements View.OnClickListener {

    private View mContentView;
    private ImageView ivMenjiang,ivHouwei,ivZhongchang,ivQianfeng;

    public SelectBallPosDialog(Context context){
        super(context);
        mContentView = getLayoutInflater().from(context).inflate(R.layout.dialog_choose_ball_pos, null);
        setContentView(mContentView);

        ivMenjiang = (ImageView) mContentView.findViewById(R.id.iv_menjiang);
        ivHouwei = (ImageView) mContentView.findViewById(R.id.iv_houwei);
        ivZhongchang = (ImageView) mContentView.findViewById(R.id.iv_zhongchang);
        ivQianfeng = (ImageView) mContentView.findViewById(R.id.iv_qianfeng);
        mContentView.findViewById(R.id.view_gap).setOnClickListener(this);
        ivMenjiang.setOnClickListener(this);
        ivHouwei.setOnClickListener(this);
        ivZhongchang.setOnClickListener(this);
        ivQianfeng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){
            case R.id.iv_menjiang:callback(4, CommonHelper.getTextByBallPos(4));break;
            case R.id.iv_houwei: callback(3,CommonHelper.getTextByBallPos(3));break;
            case R.id.iv_zhongchang:callback(2, CommonHelper.getTextByBallPos(2));break;
            case R.id.iv_qianfeng:callback(1, CommonHelper.getTextByBallPos(1));break;
        }
    }
    private void callback(int id,String text){
        if(callback !=null)
            callback.callback(id,text);
    }
}
