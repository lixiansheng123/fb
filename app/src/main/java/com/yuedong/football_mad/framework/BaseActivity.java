package com.yuedong.football_mad.framework;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.LoadDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.ViewUtils;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.MultiStateView;

import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements VolleyNetWorkCallback {
    /**
     * 第一次进入界面
     */
    private boolean one = true;
    protected RelativeLayout mMainLayout;
    protected LinearLayout mTitleLayout;
    public MultiStateView mMultiStateView;
    private LoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mMainLayout = new RelativeLayout(this);
        mTitleLayout = new LinearLayout(this);
        mMultiStateView = new MultiStateView(this);
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
        setContentView(mMainLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        loadDialog = new LoadDialog(this);
    }

    protected void showLoadView(boolean isShow){
        AnimationDrawable animationDrawable = (AnimationDrawable) loadDialog.mLoaderPic.getDrawable();
        if (isShow) {
            if (loadDialog != null && !loadDialog.isShowing()) {
                animationDrawable.start();
                if (!isFinishing())
                    loadDialog.show();
            }
        } else {
            if (loadDialog != null && loadDialog.isShowing()) {
                animationDrawable.stop();
                loadDialog.dismiss();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (one) {
            one = false;
            if (Constant.AUTO_INJECT)
                ViewUtils.inject(this);
            ui();
        }
    }

    long lastClickTime;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            long time = System.currentTimeMillis();
            long timeD = time - lastClickTime;
            lastClickTime = time;
            if (timeD <= 200)
                return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 回退
     */
    public void back() {
        LaunchWithExitUtils.back(this);
    }

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
     * 构建ui
     *
     * @param titleView
     */
    protected void buildUi(View titleView, View content) {
        if (titleView != null)
            initTitleView(titleView);
        mMultiStateView.setViewForState(content, MultiStateView.VIEW_STATE_CONTENT);
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

    private FragmentManager mFManager;
    protected Fragment mDisplayContext;

    /**
     * 切换显示的Fragment
     *
     * @param from 当前Fragment
     * @param to   要切换的Fragment
     */
    public void switchContent(Fragment from, Fragment to, int containerId) {
        if (mFManager == null)
            mFManager = getSupportFragmentManager();
        if (from == null) {
            from = getDefaultFrag();
        }
        if (mDisplayContext != to) {
            mDisplayContext = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 有没有将这碎片加入到当前Activity
                transaction.hide(from).add(containerId, to, to.getClass().getSimpleName());
                transaction.show(to).commit();
                    /*
                     * 事物commit只有异步处理 这是需要时间的 调用FragfmentManager
                     * 的executePendingTransactions可以让其马上执行，但是只能在主线程调用
                     */
                mFManager.executePendingTransactions();
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示toFragment
                mFManager.executePendingTransactions();
            }
        }
    }

    /**
     * 加载fragment
     *
     * @param fragment
     * @param containerId
     * @param isAddToBack
     */
    public void addFragment(Fragment fragment, int containerId, boolean isAddToBack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.add(containerId, fragment);
        if (isAddToBack)
            transaction.addToBackStack(null);
        // 提交修改
        transaction.commit();
    }


    protected Fragment getDefaultFrag() {
        return null;
    }
    protected abstract void ui();
    public abstract void networdSucceed(String tag, BaseResponse data);
    protected boolean autoLoadView = true;
    @Override
    public void onNetworkStart(String tag) {
        if(autoLoadView)
           showLoadView(true);
    }

    @Override
    public void onNetworkSucceed(String tag, BaseResponse data) {
        showLoadView(false);
        if (data == null) return;
        L.d("状态码" + data.getState().getCode());
        if (data.getState().getCode() != Constant.OK) {
            T.showShort(this, data.getState().getMsg());
        } else {
            networdSucceed(tag, data);
        }
    }

    @Override
    public void onNetworkError(String tag, VolleyError error) {
        showLoadView(false);
        if(error!=null)
             T.showShort(BaseActivity.this,error.getMessage());
    }
}
