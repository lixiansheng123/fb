package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.CommentListAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.framework.BaseAdapter;
import com.yuedong.football_mad.model.bean.CommentBean;
import com.yuedong.football_mad.model.bean.DbLikeRecord;
import com.yuedong.football_mad.model.bean.CommentRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RefreshProxy;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.TitleViewHelper;
import com.yuedong.football_mad.view.CommonInteractPop;
import com.yuedong.football_mad.view.DoubleTabView;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.bean.ListResponse;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.KeyBoardUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

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
    private View curTag;
    private String id;
    private String listTask,replyTask,commentTask,zanTask,cancelZanTask,collectCommentTask,commentReportTask;
    private RefreshProxy<List<CommentBean>> refreshProxy = new RefreshProxy<>();
    private String order = "0";
    private CommonInteractPop pop;
    // 评论的id
    private String commentId;
    // 需要移动到list的位置
    private int listNeedSelPos = 0;
    private int commentAction = ACTION_COMMENT;
    private final static int ACTION_REPLY = 1;
    private final static int ACTION_COMMENT = 2;
    private  CommentBean commentBean;
    private View zanItem;
    private DbUtils dbUtils;
     CommentListAdapter commentListAdapter;
    /* 赞流程是否是完整的  用于去除一些bug*/
    private boolean zanFinished = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        buildUi(new TitleViewHelper(this).getTitle1NonentityCenter(R.drawable.ic_round_return, R.drawable.ic_white_write_comment, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAction = ACTION_COMMENT;
                showEt();
            }
        }), R.layout.activity_comment_list);
        pop = new CommonInteractPop(this);
        autoLoadView = false;
        dbUtils = DbUtils.create(this);
    }

    @Override
    protected void ui() {
        pop.setOnCommentInteractCallback(new CommonInteractPop.OnCommentInteractCallback() {
            @Override
            public void onReply() {
                commentAction = ACTION_REPLY;
                showEt();
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
                User loginUser = MyApplication.getInstance().getLoginUser();
                if(loginUser == null)return;
                String sid = loginUser.getSid();
                Map<String, String> sidPostMap = DataUtils.getSidPostMap(sid);
                sidPostMap.put("commentid",commentId);
                collectCommentTask = RequestHelper.post(Constant.URL_COLLECT_COMMENT,sidPostMap,BaseResponse.class,false,false,NewsCommentListActivity.this);
            }

            @Override
            public void onFriend() {

            }

            @Override
            public void onReport() {
                Map<String,String> post = new HashMap<String, String>();
                post.put("commentid",commentId);
               commentReportTask =  RequestHelper.post(Constant.URL_COMMENT_ALARM, post, BaseResponse.class, false, false, NewsCommentListActivity.this);
            }
        });
        getCommentList(false,true);
        doubleTabView.setITagClickListener(new DoubleTabView.ITagClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position == 0) {
                    order = "0";
                    getCommentList(false,true);
                } else {
                    order = "1";
                    getCommentList(false,true);
                }
            }
        });
    }
    private int pullMode;
    private boolean mIsNeedSel;
    private void getCommentList(final boolean isNeedSel,boolean autoRefreshAnim) {
        refreshProxy.autoRefreshAnim = autoRefreshAnim;
        mIsNeedSel = isNeedSel;
        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<List<CommentBean>>() {

            @Override
            public BaseAdapter<List<CommentBean>> getAdapter(List<List<CommentBean>> data) {
                commentListAdapter = new CommentListAdapter(activity,data);
                commentListAdapter.setOnCommentClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentId = (String) v.getTag(R.string.str_key_id);
                        listNeedSelPos = (int) v.getTag(R.string.str_key_position);
                        // 获取activity根视图
                        pop.showPopupWindow(getWindow().getDecorView().findViewById(android.R.id.content));
                    }
                });
                commentListAdapter.setOnZnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!zanFinished) return;
                        zanFinished = true;
                        boolean isGoods = (boolean) v.getTag();
                        int pos = (int) v.getTag(R.string.str_key_position);
                        zanItem = (View) v.getTag(R.string.str_key_view);
                        listNeedSelPos = pos;
                        List<CommentBean> item = commentListAdapter.getItem(pos);
                        commentBean = item.get(item.size() - 1);
                        if (commentBean == null) return;
                        Map<String, String> post = new HashMap<>();
                        post.put("commentid", commentBean.getId());
//                        post.put("authorid", commentBean.getAuthor());
                        if (!isGoods) {
                            // 点赞
                            zanTask = RequestHelper.post(Constant.URL_COMMENT_ZAN, post, BaseResponse.class, false, false, NewsCommentListActivity.this);
                        }
                    }
                });
                return commentListAdapter;
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
//                if (pullMode == 1) {
//                    if (mIsNeedSel) {
//                        L.d("需要去到的位置" + listNeedSelPos);
//                        listView.setSelection(listNeedSelPos);
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
                String content = etContent.getText().toString();
                if(TextUtils.isEmpty(content)){
                    T.showShort(activity,"评论内容不能为空~");
                    return;
                }
                if(commentAction == ACTION_REPLY){
                    if(!TextUtils.isEmpty(commentId)){
                        Map<String,String> post = new HashMap<>();
                        post.put("author",loginUser.getId());
                        post.put("news",id);
                        post.put("parent",commentId);
                        post.put("content",content);
                        replyTask=  RequestHelper.post(Constant.URL_ADD_COMMENT,post,BaseResponse.class,false,false,NewsCommentListActivity.this);
                    }
                }else{
                    commentTask = CommonHelper.newsComment(loginUser.getSid(),id,content,NewsCommentListActivity.this);
                }
                ViewUtils.hideLayout(llComment);
                KeyBoardUtils.closeKeybord(etContent,activity);
                break;
        }
    }

    private void showEt(){
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
    public void onNetworkError(String tag, VolleyError error) {
        super.onNetworkError(tag, error);
        if(tag.equals(zanTask)||tag.equals(cancelZanTask))
            zanFinished = true;
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(zanTask)||tag.equals(cancelZanTask))
            zanFinished = true;
        if(tag.equals(replyTask)||tag.equals(commentTask)){
            etContent.setText("");
            getCommentList(true,false);
            if(tag.equals(replyTask))T.showShort(activity,"回复成功");
            else T.showShort(activity,"评论成功");
        }else if(tag.equals(zanTask)){
            if(commentBean == null)return;
            DbLikeRecord dbLikeRecord = new DbLikeRecord();
            dbLikeRecord.setComment_id(Integer.parseInt(commentBean.getId()));
            dbLikeRecord.setIs_goods(1);
            try {
                dbUtils.save(dbLikeRecord);
            } catch (DbException e) {
                e.printStackTrace();
            }
            zanStyleControl(true);
        }/*else if(tag.equals(cancelZanTask)){
            if(commentBean == null)return;
            try {
                DbLikeRecord likeRecord = dbUtils.findFirst(Selector.from(DbLikeRecord.class).where("comment_id","=",Integer.parseInt(commentBean.getId())));
                if(likeRecord !=null){
                    likeRecord.setIs_goods(2);
                    dbUtils.update(likeRecord);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
            zanStyleControl(false);
        }*/
        else  if(tag.equals(collectCommentTask)){
            T.showShort(activity,"收藏评论成功");
        }else if(tag.equals(commentReportTask)){
            T.showShort(activity,"评论举报成功");
        }
    }


    private void zanStyleControl(boolean isGoods){
        commentListAdapter.updateCommentGoods();
        ImageView ivZan = (ImageView) zanItem.findViewById(R.id.iv_zan);
        TextView tvZanNum = (TextView) zanItem.findViewById(R.id.tv_zan_num);
        String goodsNumStr = tvZanNum.getText().toString();
        List<List<CommentBean>> data = commentListAdapter.getData();
        List<CommentBean> commentBeans = data.get(listNeedSelPos);
        CommentBean bean = commentBeans.get(commentBeans.size() - 1);
        ivZan.setTag(isGoods);
        int goodsNumInt = -1;
        if(!TextUtils.isEmpty(goodsNumStr)){
            goodsNumInt = Integer.parseInt(goodsNumStr);
        }
        if(isGoods){
            ivZan.setImageResource(R.drawable.ic_big_blue_zan);
            if(goodsNumInt!=-1){
                goodsNumInt++;
                tvZanNum.setText(goodsNumInt+"");
                bean.setGood(goodsNumInt+"");
            }
        }else{
            ivZan.setImageResource(R.drawable.ic_big_white_zan);
            if(goodsNumInt!=-1){
                goodsNumInt--;
                tvZanNum.setText(goodsNumInt+"");
                bean.setGood(goodsNumInt + "");
            }
        }
    }
}
