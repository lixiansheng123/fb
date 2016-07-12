package com.yuedong.football_mad.ui.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.MyVideoView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.utils.T;

public class VideoActivity extends BaseActivity {
    @ViewInject(R.id.videoView)
    private MyVideoView videoView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_video);
        url = getIntent().getExtras().getString(Constant.KEY_STR);
        if(TextUtils.isEmpty(url)){
            T.showShort(activity, "播放视频源为空");
            back();
            return;
        }
        url = UrlHelper.checkUrl(url);
    }

    @Override
    protected void ui() {
        Uri uri = Uri.parse(url);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
