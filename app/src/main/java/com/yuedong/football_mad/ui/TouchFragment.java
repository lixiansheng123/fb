package com.yuedong.football_mad.ui;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.adapter.TouchAdapter;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.TouchBannerBean;
import com.yuedong.football_mad.model.bean.TouchBannerListBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.framework.BaseFragment;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.football_mad.model.helper.UrlHelper;
import com.yuedong.football_mad.view.PulltoRefreshListView;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.ioc.annotation.ViewInject;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.lib_develop.utils.DisplayImageByVolleyUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.view.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊鹏 on 2016/6/6
 */
public class TouchFragment extends BaseFragment {
    @ViewInject(R.id.listview)
    private PulltoRefreshListView pulltoRefreshListView;
    @ViewInject(R.id.iv_icon_world)
    private ImageView iconWorld;
    @ViewInject(R.id.iv_icon_china)
    private ImageView iconChina;
    private View curTab;
    private TouchAdapter TouchAdapter;
    private ImageView[] tabs = new ImageView[2];
    private String bannerTask;
    private NetworkImageView small1, small2, small3;
    private BannerView<String> bannerView;

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_touch;
    }

    @Override
    public void ui() {
        iconWorld.setTag(R.string.key_click_icon, R.drawable.ic_home_touch_world_select);
        iconWorld.setTag(R.string.key_unclick_icon, R.drawable.ic_home_touch_world_unselect);
        iconChina.setTag(R.string.key_click_icon, R.drawable.ic_home_touch_china_select);
        iconChina.setTag(R.string.key_unclick_icon, R.drawable.ic_home_touch_china_unselect);
        tabs[0] = iconWorld;
        tabs[1] = iconChina;
        List<User> users = new ArrayList<User>();
        users.add(null);
        users.add(null);
        users.add(null);
        TouchAdapter = new TouchAdapter(getContext(), users);
        // 增加header
        View listHeadView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_touch_list_head, pulltoRefreshListView, false);
        small1 = (NetworkImageView) listHeadView.findViewById(R.id.image_small1);
        small2 = (NetworkImageView) listHeadView.findViewById(R.id.image_small2);
        small3 = (NetworkImageView) listHeadView.findViewById(R.id.image_small3);
        bannerView = (BannerView<String>) listHeadView.findViewById(R.id.banner);
        pulltoRefreshListView.addHeaderView(listHeadView, null, false);
        pulltoRefreshListView.setAdapter(TouchAdapter);
        TouchAdapter.notifyDataSetChanged();
        bannerRequest(1);
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {
        if (tag.equals(bannerTask)) {
            if (data != null) {
                TouchBannerListBean listBean = (TouchBannerListBean) data;
                List<TouchBannerBean> verlist = listBean.getData().getVerlist();
                for (int i = 0; i < verlist.size(); i++) {
                    TouchBannerBean touchBannerBean = verlist.get(i);
                    switch (i) {
                        case 0:
                            small1.setTag(touchBannerBean);
                            DisplayImageByVolleyUtils.loadImage(small1, UrlHelper.checkUrl(touchBannerBean.getPic()));
                            break;
                        case 1:
                            small2.setTag(touchBannerBean);
                            DisplayImageByVolleyUtils.loadImage(small2, UrlHelper.checkUrl(touchBannerBean.getPic()));
                            break;
                        case 2:
                            small3.setTag(touchBannerBean);
                            DisplayImageByVolleyUtils.loadImage(small3, UrlHelper.checkUrl(touchBannerBean.getPic()));
                            break;
                    }
                }
                List<TouchBannerBean> horlist = listBean.getData().getHorlist();
                List<String> bannerList = new ArrayList<String>();
                for (int i = 0; i < horlist.size(); i++) {
                    bannerList.add(UrlHelper.checkUrl(horlist.get(i).getPic()));
                }
                bannerView.setIsNeedIndicator(true);
                bannerView.buildData(bannerList);
                bannerView.use();
                bannerView.setIPagerSelectListener(new BannerView.IPagerSelectListener() {
                    @Override
                    public void onPagerSelect(int index) {

                    }
                });
                bannerView.setIClickListener(new BannerView.IClickListener() {
                    @Override
                    public void onClick(ViewPager viewPager, View view, int position) {
                        T.showShort(getActivity(), "click item pos:" + position);
                    }
                });
            }
        }
    }

    private void bannerRequest(int type) {
        String url = "";
        if (type == 1) {
            url = Constant.URL_BANNEL_WORLD;
        } else {
            url = Constant.URL_BANNEL_CHINA;
        }
        bannerTask = RequestHelper.post(url, null, TouchBannerListBean.class, true, this);
    }

    @OnClick({R.id.iv_icon_world, R.id.iv_icon_china})
    public void tabClick(View view) {
        if (view == curTab) return;
        curTab = view;
        resetTabStatus(view);
        switch (view.getId()) {
            case R.id.iv_icon_world:
                bannerRequest(1);
                break;
            case R.id.iv_icon_china:
                bannerRequest(2);
                break;
        }
    }

    private void resetTabStatus(View view) {
        ImageView tabView = null;
        if (view instanceof ImageView)
            tabView = (ImageView) view;
        if (tabView == null) return;
        for (ImageView imageView : tabs) {
            imageView.setImageResource((Integer) imageView.getTag(R.string.key_unclick_icon));
        }
        tabView.setImageResource((Integer) tabView.getTag(R.string.key_click_icon));
    }
}
