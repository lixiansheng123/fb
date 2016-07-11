package com.yuedong.football_mad.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

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
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.DateUtils;

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
    private VideoListAdapter adapter;
    private int page = 1;
    private int count = Config.PAGER_SIZE;
    private int max = 0;
    private String listTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(new TitleViewHelper(this).getTitle1NomarlCenterTitle(R.drawable.ic_round_return, "视频派", R.drawable.ic_title_send, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_video_list);
        adapter = new VideoListAdapter(this);
    }

    @Override
    protected void ui() {
        videoList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
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

    private void renderUi(List<VideoBean> list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
