package com.yuedong.football_mad.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

/**
 * @author 俊鹏 on 2016/7/1
 */
public class HotGameListFragment extends BaseFragment {
    @ViewInject(R.id.listview2)
    private ListView listView;
    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.listview;
    }

    @Override
    public void ui() {

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
