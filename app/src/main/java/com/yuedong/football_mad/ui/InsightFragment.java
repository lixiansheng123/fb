package com.yuedong.football_mad.ui;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.InsihtAdapter;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊鹏 on 2016/6/7
 */
public class InsightFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @ViewInject(R.id.id_include_layout)
    private View stopView;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home_insight;
    }

    @Override
    public void ui() {
        stopView.setVisibility(View.GONE);
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        data.add(null);
        InsihtAdapter adapter2 = new InsihtAdapter(getActivity(), data);
        listView.setAdapter(adapter2);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
