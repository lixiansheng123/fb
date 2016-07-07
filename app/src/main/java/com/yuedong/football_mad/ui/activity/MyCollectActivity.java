package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.MyCollectAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CollectListBean;
import com.yuedong.football_mad.model.bean.CollectListRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.ui.fragment.CollectNewsListFragment;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏
 */
public class MyCollectActivity extends BaseActivity {
    @ViewInject(R.id.tab)
    private DoubleTabView doubleTabView;
    private CollectNewsListFragment collectNewsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle3(R.drawable.ic_round_return, "我的收藏", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_my_collect);
        initFragment();
        if(savedInstanceState == null) addFragment(collectNewsListFragment,R.id.fragment_container,false);
    }

    @Override
    protected Fragment getDefaultFrag() {
        return collectNewsListFragment;
    }

    private void initFragment() {
        collectNewsListFragment = new CollectNewsListFragment();
    }

    @Override
    protected void ui() {
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(position == 0){
                    switchContent(mDisplayContext,collectNewsListFragment,R.id.fragment_container);
                }
            }
        });
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
