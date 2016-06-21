package com.yuedong.football_mad.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.view.NewsStyleDialog;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.ViewUtils;

public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.ll_comment_box)
    private View llComment;
    private Animation outAnim, inAnim;
    @ViewInject(R.id.webView)
    private WebView webView;
    private NewsStyleDialog newsStyleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_news_detail);
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
        webView.loadUrl("http:www.baidu.com");
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.iv_icon, R.id.iv_icon2, R.id.iv_icon3, R.id.title_btn_left, R.id.title_btn_right, R.id.btn_comment,R.id.ll_font})
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
                LaunchWithExitUtils.startActivity(NewsDetailActivity.this, CommentListActivity.class);
                break;
            case R.id.btn_comment:
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
        }
    }


}
