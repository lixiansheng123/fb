package com.yuedong.football_mad.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.VideoListAdapter;
import com.yuedong.football_mad.app.Config;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.VideoBean;
import com.yuedong.football_mad.model.bean.VideoRespBean;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.SpaceItemDecoration;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.DimenUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 视频派
 */
public class VideoListActivity extends BaseActivity {
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    @ViewInject(R.id.ll_footer)
    private View footerView;
    private VideoListAdapter adapter;
    private int page = 1;
    private int count = Config.PAGER_SIZE;
    private int max = 0;
    private String listTask;
    private boolean isLoad,loadFull;
    private int[] lastPositions;
    private int type = 1;// 1刷新  2上拉更多
    //用来标记是否正在向最后一个滑动，既是否向下滑动
    boolean isSlidingToLast = false;
    private List<VideoBean> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "视频派", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_video_list);
        adapter = new VideoListAdapter(this);
        autoLoadView = false;
    }

    @Override
    protected void ui() {
        videoList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                type = 1;
                videoList();
            }
        });
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(DimenUtils.dip2px(activity, 8)));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                L.d("newState:" + newState);
                L.d("isSlidingToLast:"+isSlidingToLast);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && isSlidingToLast){
                //获取最后一个完全显示的ItemPosition
                int[] lastVisiblePositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                int lastVisiblePos = findMax(lastVisiblePositions);
                int totalItemCount = layoutManager.getItemCount();
                    if (lastVisiblePos >= totalItemCount - 1) {
                        T.showShort(activity,"尝试加载更多");
                        if (!isLoad && !loadFull){
                            ViewUtils.showLayout(footerView);
                            type = 2;
                            isLoad =true;
                            page++;
                            videoList();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                L.d("dx:"+dx);
                L.d("dy:"+dy);
                if(dy > 0){
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                }else{
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }

            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void videoList() {
        Map<String, String> post = DataUtils.getListPostMap(page + "", count + "", max + "");
        listTask =  RequestHelper.post(Constant.URL_VIDEO_LIST,post,VideoRespBean.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(listTask)){
            VideoRespBean bean = (VideoRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    @Override
    public void onNetworkSucceed(String tag, BaseResponse data) {
        super.onNetworkSucceed(tag, data);
        if(tag.equals(listTask)){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onNetworkError(String tag, VolleyError error) {
        super.onNetworkError(tag, error);
        if(tag.equals(listTask)){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void renderUi(List<VideoBean> list) {
        if(type == 1){
            data.clear();
            page = 1;
        }else{
            ViewUtils.hideLayout(footerView);
            isLoad = false;
        }
        if(list.size()<count)loadFull = true;
        data.addAll(list);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}
