package com.yuedong.football_mad.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.SpecialDetailBean;
import com.yuedong.football_mad.model.bean.SpecialDetailRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.Map;

public class SpecialDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_comment_num)
    private TextView tvCommonNum;
    @ViewInject(R.id.iv_title_right)
    private ImageView ivRightIcon;
    @ViewInject(R.id.title_btn_right)
    private View llRight;
    private String id;
    private String detailTask;
    private String attentionTask;
    private int interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(null, R.layout.activity_special_detail);
    }

    @Override
    protected void ui() {
        ViewUtils.hideLayout(tvCommonNum);
        ivRightIcon.setImageResource(R.drawable.ic_detail_collect);
        getDetail();
    }

    private void getDetail() {
        String sid = "";
        User loginUser = MyApplication.getInstance().getLoginUser();
        if(loginUser!=null) sid = loginUser.getSid();
        Map<String, String> post = DataUtils.getSidPostMap(sid);
        post.put("id", id);
        detailTask =  RequestHelper.post(Constant.URL_SPECIAL_BY_ID,post,SpecialDetailRespBean.class,false,false,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            SpecialDetailRespBean bean = (SpecialDetailRespBean) data;
            renderUi(bean.getData().getItem());
        }else if(tag.equals(attentionTask)){
            if(interest == 1){
                T.showShort(activity, "取消收藏成功");
                interest = 0;
                ivRightIcon.setImageResource(R.drawable.ic_detail_collect);
            }else {
                T.showShort(activity,"收藏成功");
                interest = 1;
                ivRightIcon.setImageResource(R.drawable.ic_detail_collect_select);
            }
        }
    }

    private void renderUi(SpecialDetailBean item) {
         interest = item.getInterest();
        if(interest == 1){
            ivRightIcon.setImageResource(R.drawable.ic_detail_collect_select);
        }
    }
    @OnClick({R.id.title_btn_left,R.id.title_btn_right})
    protected void clickEvent(View view){
        switch(view.getId()){
            case R.id.title_btn_left:
                back();
                break;

            case R.id.title_btn_right:
                    boolean isAttention = true;
                    if(interest == 1)isAttention = false;
                    attentionTask=  DataUtils.attentionNews(activity,2,id,SpecialDetailActivity.this,isAttention);
                break;
        }
    }
}
