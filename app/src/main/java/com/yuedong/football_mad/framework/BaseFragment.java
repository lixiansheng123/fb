package com.yuedong.football_mad.framework;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.ViewUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.view.MultiStateView;

/**
 * @author 俊鹏 on 2016/6/6
 */
public abstract class BaseFragment extends Fragment implements VolleyNetWorkCallback {
    protected RelativeLayout mMainLayout;
    protected LinearLayout mTitleLayout;
    public MultiStateView mMultiStateView;
    private boolean one = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();
        return mMainLayout;
    }

    private void init() {
        mMainLayout = new RelativeLayout(getActivity());
        mTitleLayout = new LinearLayout(getActivity());
        mMultiStateView = new MultiStateView(getActivity());
        // 统一设一个id
        mTitleLayout.setId(R.id.id_title);
        mMultiStateView.setId(R.id.id_content);
        RelativeLayout.LayoutParams titleLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        mMainLayout.addView(mTitleLayout, titleLp);
        RelativeLayout.LayoutParams contentLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentLp.addRule(RelativeLayout.BELOW, mTitleLayout.getId());
        mMainLayout.addView(mMultiStateView, contentLp);
        mMainLayout.setBackgroundResource(android.R.color.white);
        buildUi(getTitleView(), getContentView());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (one) {
            one = false;
            if (Constant.AUTO_INJECT)
                ViewUtils.inject(this, mMainLayout);
            ui();
        }
    }

    protected abstract View getTitleView();

    protected abstract int getContentView();

    /**
     * 构建ui
     *
     * @param titleView
     */
    protected void buildUi(View titleView, @LayoutRes int contentResId) {
        if (titleView != null)
            initTitleView(titleView);
        mMultiStateView.setViewForState(contentResId, MultiStateView.VIEW_STATE_CONTENT);
    }

    /**
     * 更新状态view
     *
     * @param emptyView
     * @param loadView
     * @param errorView
     */
    protected void initStateView(@LayoutRes int emptyView, @LayoutRes int loadView, @LayoutRes int errorView) {
        mMultiStateView.setViewForState(emptyView, MultiStateView.VIEW_STATE_EMPTY);
        mMultiStateView.setViewForState(loadView, MultiStateView.VIEW_STATE_LOADING);
        mMultiStateView.setViewForState(errorView, MultiStateView.VIEW_STATE_ERROR);
    }

    /**
     * 初始化title布局
     *
     * @param titleView
     */
    private void initTitleView(View titleView) {
        mTitleLayout.removeAllViews();
        if (titleView.getParent() != null) {
            ViewGroup parent = (ViewGroup) titleView.getParent();
            parent.removeView(titleView);
        }
        mTitleLayout.addView(titleView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Config.TITLE_HEIGHT));
    }

    public abstract void ui();

    public abstract void networdSucceed(String tag, BaseResponse data);

    @Override
    public void onNetworkStart(String tag) {
        L.d(tag + "请求开始");
    }

    @Override
    public void onNetworkSucceed(String tag, BaseResponse data) {
        if (data == null) return;
        L.d("状态码" + data.getState().getCode());
        if (data.getState().getCode() != Constant.OK) {
            T.showShort(getActivity(), data.getState().getMsg());
        } else {
            networdSucceed(tag, data);
        }
    }

    @Override
    public void onNetworkError(String tag, VolleyError error) {
        L.d(tag + "请求失败");
        T.showShort(getActivity(), error.getMessage());
    }
}
