package com.yuedong.football_mad.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.T;
@SuppressLint("SetJavaScriptEnabled")
public class SendPostActivity extends BaseActivity {
    @ViewInject(R.id.webView)
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_send_post);
    }

    @Override
    protected void ui() {
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://192.168.0.110/utf8-php/editor.html");
        webView.addJavascriptInterface(new JavaScriptObject(activity), "jsObj");
    }

    public class JavaScriptObject {
        Context mContxt;
        //sdk17版本以上加上注解
        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }
        @JavascriptInterface
        public void toHtml(String name) {
            Toast.makeText(mContxt, name, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick({R.id.btn_pic,R.id.btn_bold,R.id.btn_html,R.id.btn_alert})
    protected void clickEvent(View view){
        switch (view.getId()){
            case R.id.btn_pic:
            webView.loadUrl("javascript:insertimage('http://img0.imgtn.bdimg.com/it/u=3686744340,1572931870&fm=21&gp=0.jpg')");
                break;
            case R.id.btn_alert:
                webView.loadUrl("javascript:appAlert('这是一个测试内容')");
                break;
            case R.id.btn_bold:
                webView.loadUrl("javascript:bold()");
                break;

            case R.id.btn_html:
                webView.loadUrl("javascript:getContent(2)");
                break;


        }
    }
}
