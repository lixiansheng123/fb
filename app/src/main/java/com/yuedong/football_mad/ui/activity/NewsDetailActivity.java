package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.DbLikeRecord;
import com.yuedong.football_mad.model.bean.NewsDetailBean;
import com.yuedong.football_mad.model.bean.NewsDetailRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.CommonHelper;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.CriticismPop;
import com.yuedong.football_mad.view.NewsStyleDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;
import com.yuedong.lib_develop.view.RoundImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.ll_comment)
    private View llComment;
    @ViewInject(R.id.webView)
    private WebView webView;
    @ViewInject(R.id.et_content)
    private EditText etCotnent;
    @ViewInject(R.id.iv_user_head)
    private RoundImageView ivUserHead;
    @ViewInject(R.id.rl_head)
    private View rlHead;
    @ViewInject(R.id.tv_name)
    private TextView tvUserName;
    @ViewInject(R.id.tv_level)
    private TextView tvLevel;
    @ViewInject(R.id.tv_zan_num)
    private TextView tvZanNum;
    @ViewInject(R.id.tv_cai_num)
    private TextView tvCaiNum;
    @ViewInject(R.id.tv_comment_num)
    private TextView tvCommentNum;
    @ViewInject(R.id.iv_zan)
    private ImageView ivZan;
    @ViewInject(R.id.iv_cai)
    private ImageView ivCai;
    @ViewInject(R.id.iv_collect)
    private ImageView ivCollect;
    @ViewInject(R.id.ll_attention)
    private View llAttention;
    private String commentTask,detailTask,newsGoodsTask,caiTask;
    private NewsStyleDialog newsStyleDialog;
    private String id;
    private Animation outAnim, inAnim;
    private CriticismPop criticismPop;
    private DbUtils db;
    private String attentionTask;
    private boolean canZan = true,canCai = true;
    private int interest; //1已经收藏 2未收藏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_news_detail);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        outAnim = AnimationUtils.loadAnimation(this, R.anim.anim_decline);
        inAnim = AnimationUtils.loadAnimation(this, R.anim.anim_go_up);
        newsStyleDialog = new NewsStyleDialog(this);
        db = DbUtils.create(this);
        criticismPop = new CriticismPop(this);
    }

    @Override
    protected void ui() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.baidu.com");
        getDetailInfo();
        // 赞和踩在本地查询
        goodsAndTrampleControl();
        criticismPop.setOnCriticismCallback(new CriticismPop.OnCriticismCallback() {
            @Override
            public void callback(int type) {
                Map<String, String> post = new HashMap<String, String>();
                post.put("newsid", id);
                String url = Constant.URL_NEWS_NOGOODS;
                if (type == 1)
                    url = Constant.URL_NEWS_AD_MORE;
                else if (type == 2)
                    url = Constant.URL_NEWS_PLAGIARIZE;
                caiTask = RequestHelper.post(url, post, BaseResponse.class, false, false, NewsDetailActivity.this);
            }
        });
    }

    private void goodsAndTrampleControl() {
        int[] ints = new int[]{1,3};
        try {
            List<DbLikeRecord> datas = db.findAll(Selector.from(DbLikeRecord.class).where("like_type","=",2).and("is_goods","IN",ints));
            if(datas!=null){
//                L.d(datas.toString());
                for(DbLikeRecord dbLikeRecord : datas) {
                    if (dbLikeRecord.getComment_id() == Integer.parseInt(id)) {
                        if (dbLikeRecord.getIs_goods() == 1) {
                            canZan = false;
                            ivZan.setSelected(true);
                        } else if (dbLikeRecord.getIs_goods() == 3) {
                            canCai = false;
                            ivCai.setSelected(true);

                        }
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        L.d("zan"+canZan+"=cai"+canCai);
    }

    private void getDetailInfo() {
        User loginUser = MyApplication.getInstance().getLoginUser();
        String sid = "";
        if (loginUser != null) sid = loginUser.getSid();
        Map<String, String> post = DataUtils.getSidPostMap(sid);
        post.put("id", id);
        detailTask = RequestHelper.post(Constant.URL_NEWS_BY_ID,post, NewsDetailRespBean.class,true,true,this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(commentTask)){
            T.showShort(activity, "评论成功");
        }else if(tag.equals(detailTask)){
            NewsDetailRespBean bean = (NewsDetailRespBean) data;
            renderUi(bean.getData().getList());
        }else if(tag.equals(newsGoodsTask)){
            int zanNum = Integer.parseInt(tvZanNum.getText().toString());
            zanNum++;
            tvZanNum.setText(zanNum+"");
            canZan = false;
            ivZan.setSelected(true);
            DbLikeRecord dbLikeRecord = new DbLikeRecord();
            dbLikeRecord.setLike_type(2);
            dbLikeRecord.setIs_goods(1);
            dbLikeRecord.setComment_id(Integer.parseInt(id));
            try {
                db.save(dbLikeRecord);
            } catch (DbException e) {
                e.printStackTrace();
            }

        }else if(tag.equals(caiTask)){
        int caiNum = Integer.parseInt(tvCaiNum.getText().toString()) ;
            caiNum++;
            tvCaiNum.setText(caiNum+"");
            canCai = false;
            ivCai.setSelected(true);
            DbLikeRecord dbLikeRecord = new DbLikeRecord();
            dbLikeRecord.setLike_type(2);
            dbLikeRecord.setIs_goods(3);
            dbLikeRecord.setComment_id(Integer.parseInt(id));
            try {
                db.save(dbLikeRecord);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }else if(tag.equals(attentionTask)){
            if(interest == 1){
                T.showShort(activity,"取消收藏成功");
                interest = 0;
                ivCollect.setSelected(false);
            }else {
                T.showShort(activity,"收藏成功");
                interest = 1;
                ivCollect.setSelected(true);
            }
        }
    }

    private void renderUi(NewsDetailBean list) {
        int caiNum = list.getPoorwrite()+list.getRubishadv()+list.getBadcopy();
        tvZanNum.setText(list.getGood());
        tvCommentNum.setText(list.getComment());
        tvCaiNum.setText(caiNum + "");
        L.d("视频路径" + list.getVedios());
        interest = list.getInterest();

        if(interest == 1){
            ivCollect.setSelected(true);
        }
        llAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAttention = true;
                if(interest == 1)
                  isAttention = false;
                attentionTask =  DataUtils.attentionNews(activity,1,id,NewsDetailActivity.this,isAttention);
            }
        });

    }

    @OnClick({R.id.iv_icon, R.id.iv_icon2, R.id.iv_icon3, R.id.title_btn_left, R.id.title_btn_right, R.id.btn_comment,R.id.ll_font,R.id.iv_pack_up,R.id.iv_send,R.id.ll_zan,R.id.ll_cai})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.iv_icon2:
                break;
            case R.id.iv_icon3:
                break;
            case R.id.title_btn_left:
                back();
                break;
            case R.id.title_btn_right:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_ID,id);
                LaunchWithExitUtils.startActivity(NewsDetailActivity.this, NewsCommentListActivity.class,bundle);
                break;
            case R.id.btn_comment:
            case R.id.iv_pack_up:
                if (llComment.getVisibility() == View.GONE) {
                    llComment.startAnimation(inAnim);
                    ViewUtils.showLayout(llComment);
                } else {
                    llComment.startAnimation(outAnim);
                    ViewUtils.hideLayout(llComment);
                }
                break;

            case R.id.ll_font:
                newsStyleDialog.show();
                break;

            case R.id.iv_send:
                // 判断登录
                User loginUser = MyApplication.getInstance().getLoginUser();
                if(loginUser == null) {
                    LaunchWithExitUtils.startActivity(activity,LoginActivity.class);
                    return;
                }
                String content = etCotnent.getText().toString();
                if(TextUtils.isEmpty(content)) {
                    T.showShort(activity,"请输入评论内容");
                    return;
                }
               commentTask =  CommonHelper.newsComment(loginUser.getSid(),id,content,NewsDetailActivity.this);
                llComment.startAnimation(outAnim);
                ViewUtils.hideLayout(llComment);
                break;

            case R.id.ll_zan:
                if(!canZan)return;
                Map<String,String> post = new HashMap<>();
                post.put("newsid",id);
                newsGoodsTask = RequestHelper.post(Constant.URL_NEWS_GOODS,post,BaseResponse.class,false,false,NewsDetailActivity.this);
                break;

            case R.id.ll_cai:
                if(!canCai)return;
                criticismPop.showPopToTop(view);
                break;

        }
    }


}
