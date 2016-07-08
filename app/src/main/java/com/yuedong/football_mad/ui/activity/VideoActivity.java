package com.yuedong.football_mad.ui.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.view.MyVideoView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;

public class VideoActivity extends BaseActivity {
    @ViewInject(R.id.videoView)
    private MyVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_video);
    }

    @Override
    protected void ui() {
        Uri uri = Uri.parse("http://192.168.0.113/7108_b_h264_sd_960_540.mp4");
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }
}
