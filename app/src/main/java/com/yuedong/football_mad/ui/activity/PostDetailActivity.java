package com.yuedong.football_mad.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.bean.NewsDetailBean;
import com.yuedong.football_mad.model.bean.NewsDetailRespBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.model.helper.DataUtils;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.ViewUtils;

import java.util.Map;

public class PostDetailActivity extends BaseActivity {
    @ViewInject(R.id.iv_title_right)
    private ImageView ivTitleRight;
    @ViewInject(R.id.tv_comment_num)
    private TextView tvCommentNum;
    @ViewInject(R.id.webView)
    private WebView webView;
    private String id;
    private String detailTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_post_detail);
        id = getIntent().getExtras().getString(Constant.KEY_ID);
    }

    @Override
    protected void ui() {
        ViewUtils.hideLayout(tvCommentNum);
        ivTitleRight.setImageResource(R.drawable.ic_title_send);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.baidu.com");
//        getDetailInfo();
    }

    private void getDetailInfo() {
        User loginUser = MyApplication.getInstance().getLoginUser();
        String sid = "";
        if (loginUser != null) sid = loginUser.getSid();
        Map<String, String> post = DataUtils.getSidPostMap(sid);
        post.put("id", id);
        detailTask = RequestHelper.post(Constant.URL_NEWS_BY_ID, post, NewsDetailRespBean.class, true, true, this);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if(tag.equals(detailTask)){
            NewsDetailRespBean bean = (NewsDetailRespBean) data;
            renderUi(bean.getData().getList());
        }
    }

    private void renderUi(NewsDetailBean list) {

    }

    @OnClick({R.id.title_btn_left})
    protected void clickEvent(View view){
        switch(view.getId()){
            case R.id.title_btn_left:
                back();
                break;
        }
    }
}
