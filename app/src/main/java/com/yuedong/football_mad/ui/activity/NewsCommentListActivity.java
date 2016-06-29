package com.yuedong.football_mad.ui.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.CommentRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.CommonInteractPop;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.KeyBoardUtils;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsCommentListActivity extends BaseActivity {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView listView;
    @ViewInject(R.id.doubleTabView)
    private DoubleTabView doubleTabView;
    @ViewInject(R.id.ll_comment)
    private View llComment;
    @ViewInject(R.id.et_content)
    private EditText etContent;
//    @ViewInject(R.id.iv_pack_up)
//    private ImageView ivPackup;
//    @ViewInject(R.id.iv_send)
//    private ImageView ivSend;
    private View curTag;
    private String id;
    private String listTask,replyTask;
    private RefreshProxy<List<CommentBean>> refreshProxy = new RefreshProxy<>();
    private String order = "0";
    private CommonInteractPop pop;
    // 评论的id
    private String commentId;
    // 评论在list的位置
    private int commentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(new TitleViewHelper(this).getTitle1NonentityCenter(R.drawable.ic_round_return, R.drawable.ic_white_write_comment, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }), R.layout.activity_comment_list);
        pop = new CommonInteractPop(this);
    }

    @Override
    protected void ui() {
        pop.setOnCommentInteractCallback(new CommonInteractPop.OnCommentInteractCallback() {
            @Override
            public void onReply() {
                mainHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewUtils.showLayout(llComment);
                        KeyBoardUtils.openKeybord(etContent,activity);
                        etContent.setFocusable(true);
                        etContent.setFocusableInTouchMode(true);
                        etContent.requestFocus();
                    }
                },1000);
            }

            @Override
            public void onCopy() {

            }

            @Override
            public void onWeixin() {

            }

            @Override
            public void onWeibo() {

            }

            @Override
            public void onCollect() {

            }

            @Override
            public void onFriend() {

            }

            @Override
            public void onReport() {

            }
        });
        getCommentList(false);
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    order = "0";
                    getCommentList(false);
                } else {
                    order = "1";
                    getCommentList(false);
                }
            }
        });
    }
    private int pullMode;
    private boolean mIsReply;
    private void getCommentList(final boolean isReply) {
        mIsReply = isReply;
        refreshProxy.setEmptyUi();
        refreshProxy.setEmpty();
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<List<CommentBean>>() {

            @Override
            public BaseAdapter<List<CommentBean>> getAdapter(List<List<CommentBean>> data) {
                CommentListAdapter adapter = new CommentListAdapter(activity,data);
                adapter.setOnCommentClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentId = (String)v.getTag(R.string.str_key_id);
                        commentPosition = (int) v.getTag(R.string.str_key_position);
                        // 获取activity根视图
                        pop.showPopupWindow(getWindow().getDecorView().findViewById(android.R.id.content));
                    }
                });
                return adapter;
            }

            @Override
            public void executeTask(int page, int count, int max, VolleyNetWorkCallback listener, int type) {
                pullMode = type;
                Map<String, String> post = new HashMap<>();
                post.put("newsid", id);
                post.put("count", count + "");
                post.put("pageindex", page + "");
                post.put("order", order);
                post.put("max", max + "");
                boolean useCache = false;
                if (type == 1)
                    useCache = true;
                listTask = RequestHelper.post(Constant.URL_GET_COMMENT_BY_NEWS, post, CommentRespBean.class, true, useCache, listener);
            }

            @Override
            public void networkSucceed(ListResponse<List<CommentBean>> list) {
//                if(pullMode == 1) {
//                    if (mIsReply){
//                        mIsReply = false;
//                        listView.setSelection(commentPosition);
//                    }
//                }
            }

            @Override
            public void contentIsEmpty() {

            }
        });
    }
    @OnClick({R.id.iv_pack_up,R.id.iv_send})
    protected void clickEvent(View view){
        switch (view.getId()){
            case R.id.iv_pack_up:
                ViewUtils.hideLayout(llComment);
                break;
            case R.id.iv_send:
                User loginUser = CommonHelper.checkLogin(activity);
                if(loginUser == null){
                    return;
                }
                if(TextUtils.isEmpty(commentId)){
                    T.showShort(activity,"发生未知错误,请重试");
                    return;
                }
                String content = etContent.getText().toString();
                if(TextUtils.isEmpty(content)){
                    T.showShort(activity,"评论内容不能为空~");
                    return;
                }
                Map<String,String> post = new HashMap<>();
                post.put("author",loginUser.getId());
                post.put("news",id);
                post.put("parent",commentId);
                post.put("content",content);
                replyTask=  RequestHelper.post(Constant.URL_ADD_COMMENT,post,BaseResponse.class,false,false,NewsCommentListActivity.this);
                ViewUtils.hideLayout(llComment);
                KeyBoardUtils.closeKeybord(etContent,activity);
                break;
        }
    }


    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(replyTask)){
            etContent.setText("");
            getCommentList(true);
        }
    }
}
