package com.yuedong.football_mad.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyHonorAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.HonorBean;
import com.yuedong.football_mad.model.bean.HonorRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.view.SupportScrollConflictGridView;

import java.util.List;

public class MyHonorActivity extends BaseActivity {
    private List<HonorBean> myHonorList;
    private MyHonorAdapter adapter;
    @ViewInject(R.id.gridview)
    private SupportScrollConflictGridView gridView;
    @ViewInject(R.id.gridview2)
    private SupportScrollConflictGridView gridView2;
    private String allHonorTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myHonorList = (List<HonorBean>) getIntent().getExtras().getSerializable(Constant.KEY_SERIALIZABLE);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "我的荣誉", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }),R.layout.activity_my_honor);
    }

    @Override
    protected void ui() {
        adapter = new MyHonorAdapter(activity,myHonorList);
        gridView.setAdapter(adapter);
        // 获取所有荣誉
        getAllHonor();
    }

    private void getAllHonor() {
        String sid = MyApplication.getInstance().getLoginUser().getSid();
        allHonorTask = RequestHelper.post(Constant.URL_ALL_HONOR, DataUtils.getSidPostMap(sid), HonorRespBean.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(allHonorTask)){
            HonorRespBean bean = (HonorRespBean) data;
            MyHonorAdapter adapter = new MyHonorAdapter(activity,bean.getData().getList());
            adapter.all = true;
            gridView2.setAdapter(adapter);
        }
    }
}
