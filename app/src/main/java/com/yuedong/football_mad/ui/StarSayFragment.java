package com.yuedong.football_mad.ui;

import android.view.View;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.StartSayAdapter;
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
public class StarSayFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_star_say;
    }

    @Override
    public void ui() {
        List<User> data = new ArrayList<User>();
        data.add(null);
        data.add(null);
        data.add(null);
        StartSayAdapter adapter = new StartSayAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
