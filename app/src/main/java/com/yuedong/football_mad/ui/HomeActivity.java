package com.yuedong.football_mad.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.framework.SideActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;

/**
 * @author
 */
public class HomeActivity extends SideActivity {
    private TouchFragment touchFragment;
    private InsightFragment insightFragment;
    private StarSayFragment starSayFragment;
    private SpecialFragment specialFragment;
    private SixSixSixFragment sixSixSixFragment;
    @ViewInject(R.id.btn_touch)
    private ImageView tab1;
    @ViewInject(R.id.btn_insight)
    private ImageView tab2;
    @ViewInject(R.id.btn_666)
    private ImageView tab3;
    @ViewInject(R.id.btn_say)
    private ImageView tab4;
    @ViewInject(R.id.btn_special)
    private ImageView tab5;
    private ImageView[] tabs = new ImageView[5];
    private View curTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_home);
        initFragment();
        if (savedInstanceState == null) {
            addFragment(touchFragment, R.id.fragment_container, false);
        }
    }

    private void initFragment() {
        touchFragment = new TouchFragment();
        insightFragment = new InsightFragment();
        starSayFragment = new StarSayFragment();
        specialFragment = new SpecialFragment();
        sixSixSixFragment = new SixSixSixFragment();
    }

    @Override
    protected void ui() {
        tabs[0] = tab1;
        tabs[1] = tab2;
        tabs[2] = tab3;
        tabs[3] = tab4;
        tabs[4] = tab5;
        tab1.setTag(R.string.key_click_icon, R.drawable.ic_touch_select);
        tab1.setTag(R.string.key_unclick_icon, R.drawable.ic_touch_unselect);
        tab2.setTag(R.string.key_click_icon, R.drawable.ic_insight_select);
        tab2.setTag(R.string.key_unclick_icon, R.drawable.ic_insight_unselect);
        tab3.setTag(R.string.key_click_icon, R.drawable.ic_666_select);
        tab3.setTag(R.string.key_unclick_icon, R.drawable.ic_666_unseelct);
        tab4.setTag(R.string.key_click_icon, R.drawable.ic_star_say_select);
        tab4.setTag(R.string.key_unclick_icon, R.drawable.ic_star_say_unselect);
        tab5.setTag(R.string.key_click_icon, R.drawable.ic_special_select);
        tab5.setTag(R.string.key_unclick_icon, R.drawable.ic_special_unselect);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @Override
    protected Fragment getDefaultFrag() {
        return touchFragment;
    }

    @OnClick({R.id.btn_touch, R.id.btn_insight, R.id.btn_666, R.id.btn_say, R.id.btn_special})
    public void tabClick(View view) {
        if (view == curTabView) return;
        resetTabStatus(view);
        curTabView = view;
        switch (view.getId()) {
            case R.id.btn_touch:
                switchContent(mDisplayContext, touchFragment, R.id.fragment_container);
                break;

            case R.id.btn_insight:
                switchContent(mDisplayContext, insightFragment, R.id.fragment_container);
                break;

            case R.id.btn_666:
                switchContent(mDisplayContext, sixSixSixFragment, R.id.fragment_container);
                break;

            case R.id.btn_say:
                switchContent(mDisplayContext, starSayFragment, R.id.fragment_container);
                break;

            case R.id.btn_special:
                switchContent(mDisplayContext, specialFragment, R.id.fragment_container);
                break;
        }
    }

    @OnClick({R.id.title_btn_left, R.id.title_btn_right})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.title_btn_left:
                toggle(false);
                break;
        }
    }

    /**
     * 重置tab的样式
     *
     * @param clickView
     */
    private void resetTabStatus(View clickView) {
        ImageView clickIv = null;
        if (clickView instanceof ImageView)
            clickIv = (ImageView) clickView;
        if (clickIv == null) return;
        for (ImageView imageView : tabs) {
            imageView.setImageResource((Integer) imageView.getTag(R.string.key_unclick_icon));
        }
        clickIv.setImageResource((Integer) clickIv.getTag(R.string.key_click_icon));
    }
}
