package com.yuedong.football_mad.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.lib_develop.utils.L;


public class PulltoRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final String TAG = "PulltoRefreshListView";
    // 区分PULL和RELEASE的距离的大小
    private static final int SPACE = 20;
    // 定义header的四种状态和当前状态
    private static final int NONE = 0;
    private static final int PULL = 1;
    private static final int RELEASE = 2;
    private static final int REFRESHING = 3;
    private int state;
    private LayoutInflater inflater;
    private View header;
    private View footer;
    private TextView leftTip;
    private TextView rightTip;
    private TextView more;
    private int startY;
    private int firstVisibleItem;
    private int scrollState;
    private int headerContentHeight;
    private int footerContentHeight;
    // 只有在listview第一个item显示的时候（listview滑到了顶部）才进行下拉刷新， 否则此时的下拉只是滑动listview
    private boolean isRecorded;
    private boolean isLoading;// 判断是否正在加载
    private boolean isRefresh; // 判断是否正在刷新
    private boolean isLoadFull; // 没有更多数据了

    private boolean mIsLoad=true; //是否加载更多

    private OnRefreshListener onRefreshListener;

    public PulltoRefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public PulltoRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PulltoRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    // 下拉刷新监听
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    // 初始化组件
    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer_layout, null);
        more = (TextView) footer.findViewById(R.id.id_tips);
        measureView(footer);
        footerContentHeight = footer.getMeasuredHeight();
        header = inflater.inflate(R.layout.list_pull_head, null);
        leftTip = (TextView) header.findViewById(R.id.tv_left_tip);
        rightTip = (TextView) header.findViewById(R.id.tv_right_tip);
        // 为listview添加头部和尾部，并进行初始化
        measureView(header);
        headerContentHeight = header.getMeasuredHeight();
        headTopPadding(-headerContentHeight);
        footerTopPadding(-footerContentHeight);
        this.addHeaderView(header, null, false);
        this.addFooterView(footer, null, false);
        this.setOnScrollListener(this);
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        ifNeedLoad(view, scrollState);
    }

    // 根据listview滑动的状态判断是否需要加载更多
    private void ifNeedLoad(AbsListView view, int scrollState) {
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && view.getLastVisiblePosition() == view//
                    .getPositionForView(footer) //
                    && !isLoadFull && !isRefresh && !isLoading) {
                if(mIsLoad) {
                    footerTopPadding(0);
                    setSelection(getBottom());
                    onLoad();
                }
            }
        } catch (Exception e) {
        }
    }


    /**
     * 监听触摸事件，解读手势
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRecorded = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (state == PULL) {
                    state = NONE;
                    refreshHeaderViewByState();
                } else if (state == RELEASE) {
                    state = REFRESHING;
                    refreshHeaderViewByState();
                    onRefresh();
                }
                isRecorded = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (firstVisibleItem == 0 && !isRecorded) {
                    isRecorded = true;
                    startY = (int) ev.getY();
                }
                whenMove(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }

    // 解读手势，刷新header状态
    private void whenMove(MotionEvent ev) {
        if (!isRecorded) {
            return;
        }
        int tmpY = (int) ev.getY();
        int space = tmpY - startY;
        int topPadding = space - headerContentHeight;
        if(topPadding >0)
           topPadding /= 3;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    refreshHeaderViewByState();
                }
                break;
            case PULL:
                headTopPadding(topPadding);
                if (/*scrollState == SCROLL_STATE_TOUCH_SCROLL
                        &&*/ space > headerContentHeight + SPACE) {
                    state = RELEASE;
                    refreshHeaderViewByState();
                }
                break;
            case RELEASE:
                headTopPadding(topPadding);
                if (space > 0 && space < headerContentHeight + SPACE) {
                    state = PULL;
                    refreshHeaderViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    refreshHeaderViewByState();
                }
                break;
        }

    }

    // 调整header的大小。其实调整的只是距离顶部的高度。
    private void headTopPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    private void footerTopPadding(int footerPaddingTop) {
        footer.setPadding(footer.getPaddingLeft(), footerPaddingTop, footer.getPaddingRight(), footer.getPaddingBottom());
        footer.invalidate();
    }


    // 根据当前状态，调整header
    private void refreshHeaderViewByState() {
        switch (state) {
            case NONE:
                translateAnim(header.getPaddingTop(), -headerContentHeight);
                leftTip.setText("下拉");
                break;
            case PULL:
                leftTip.setText("下拉");
                break;
            case RELEASE:
                leftTip.setText("松开手");
                break;
            case REFRESHING:
                translateAnim(header.getPaddingTop(), 0);
                leftTip.setText("正在");
                break;
        }
    }
    /**
     * 设置 是否 上拉 加载更多
     * @param isLoad
     */
    public void setIsLoad(boolean isLoad){
        this.mIsLoad=isLoad;
    }

    public void onRefresh() {
        isRefresh = true;
        if (isLoadFull) {
            isLoadFull = false;
            footerTopPadding(-footerContentHeight);
        }
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public void onLoad() {
        isLoading = true;
        more.setText("努力加载中");
        if (onRefreshListener != null) {
            onRefreshListener.onLoad();
        }
    }

    public void onRefreshComplete() {
        if (isRefresh) {
            isRefresh = false;
            state = NONE;
            refreshHeaderViewByState();
        }
        if (isLoading) {
            isLoading = false;
            if (!isLoadFull)
                footerTopPadding(-footerContentHeight);
        }

    }

    /**
     * 设置没有更多数据了
     */
    public void setLoadFull() {
        isLoadFull = true;
        more.setText("没有更多了");
        footerTopPadding(0);
    }


    public void autoRefresh() {
        state = REFRESHING;
        refreshHeaderViewByState();
        onRefresh();
    }

    // 用来计算header大小的。比较隐晦。
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void translateAnim(int fromY, int toY) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromY, toY);
        valueAnimator.setTarget(header);
        valueAnimator.setDuration(400);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                headTopPadding(animatedValue);
            }
        });
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoad();
    }

}
