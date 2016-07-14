package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

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
import com.yuedong.football_mad.ui.fragment.CollectCommentListFragment;
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
    private CollectCommentListFragment collectCommentListFragment;
    private ImageView ivTitle3Right;
    private  boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleViewHelper titleViewHelper = new TitleViewHelper(this);
        buildUi(titleViewHelper.getTitle3(R.drawable.ic_round_return, "我的收藏", null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdit){
                    isEdit = true;
                    ivTitle3Right.setImageResource(R.drawable.ic_green_finish);
                }else{
                    isEdit = false;
                    ivTitle3Right.setImageResource(R.drawable.ic_white_edit);
                }
                collectNewsListFragment.editList(isEdit);
                collectCommentListFragment.editList(isEdit);
            }
        }), R.layout.activity_my_collect);
        ivTitle3Right=  titleViewHelper.getTitle3Right();
        initFragment();
        if(savedInstanceState == null) addFragment(collectNewsListFragment,R.id.fragment_container,false);
    }

    @Override
    protected Fragment getDefaultFrag() {
        return collectNewsListFragment;
    }

    private void initFragment() {
        collectNewsListFragment = new CollectNewsListFragment();
        collectCommentListFragment = new CollectCommentListFragment();
    }

    @Override
    protected void ui() {
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                Fragment f = null;
                if(position == 0){
                    f = collectNewsListFragment;
                }else{
                    f = collectCommentListFragment;
                }
                switchContent(mDisplayContext,f,R.id.fragment_container);
            }
        });
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
