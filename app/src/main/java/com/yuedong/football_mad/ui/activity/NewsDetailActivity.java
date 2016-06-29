package com.yuedong.football_mad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.view.NewsStyleDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.ll_comment)
    private View llComment;
    private Animation outAnim, inAnim;
    @ViewInject(R.id.webView)
    private WebView webView;
    private NewsStyleDialog newsStyleDialog;
    private String id;
    @ViewInject(R.id.et_content)
    private EditText etCotnent;
    private String commentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_news_detail);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
        outAnim = AnimationUtils.loadAnimation(this, R.anim.anim_decline);
        inAnim = AnimationUtils.loadAnimation(this, R.anim.anim_go_up);
        newsStyleDialog = new NewsStyleDialog(this);
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
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(commentTask)){
            T.showShort(activity,"评论成功");
        }
    }

    @OnClick({R.id.iv_icon, R.id.iv_icon2, R.id.iv_icon3, R.id.title_btn_left, R.id.title_btn_right, R.id.btn_comment,R.id.ll_font,R.id.iv_pack_up,R.id.iv_send})
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
                Map<String,String> post = new HashMap<>();
                post.put("author",loginUser.getId());
                post.put("news",id);
                post.put("parent","0");
                post.put("content",content);
                commentTask = RequestHelper.post(Constant.URL_ADD_COMMENT,post,BaseResponse.class,false,false,NewsDetailActivity.this);
                llComment.startAnimation(outAnim);
                ViewUtils.hideLayout(llComment);
                post = null;
                break;

        }
    }


}
