package com.yuedong.football_mad.ui;

import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.yuedong.lib_develop.ioc.annotation.event.OnClick;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.framework.BaseActivity;
import com.yuedong.football_mad.model.helper.RequestHelper;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.SignUtils;
import com.yuedong.lib_develop.utils.T;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    //    private PulltoRefreshListView listView;
//    private RefreshProxy<User> refreshProxy = new RefreshProxy<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUi(null, R.layout.activity_main);
//        setContentView(R.layout.activity_main);
//        initView();
//        test();
//        test2();
    }

    @Override
    protected void ui() {
    }

    @Override
    public void networdSucceed(String tag, BaseResponse data) {

    }

    @OnClick(R.id.btn_test)
    public void click(View v) {
        T.showShort(MainActivity.this, "test click succeed....");
    }

//    private void test2() {
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("name", "123");
//        data.put("password", SignUtils.md5("123123131"));
//        RequestHelper.post(Constant.URL_LONGIN, data, null, false, new VolleyNetWorkCallback() {
//            @Override
//            public void onNetworkStart(String tag) {
//                L.d(tag + "开始请求");
//            }
//
//            @Override
//            public void onNetworkSucceed(String tag, BaseResponse data) {
//                L.d(tag + "请求成功");
//            }
//
//            @Override
//            public void onNetworkError(String tag, VolleyError error) {
//                L.d(tag + "请求失败");
//                L.d(error.getMessage());
//            }
//        });
//    }

//    private void initView() {
//        listView = (PulltoRefreshListView) findViewById(R.id.id_list);
//    }
//
//    private void test() {
//        refreshProxy.setPulltoRefreshRefreshProxy(listView, new RefreshProxy.ProxyRefreshListener<User>() {
//            @Override
//            public BaseAdapter<User> getAdapter(List<User> data) {
//                return null;
//            }
//
//            @Override
//            public void executeTask(int page, int limit, VolleyNetWorkCallback listener) {
//                String url = "http://www.zqfeng.com/crazyfootball/athlete/getlist";
//                BaseRequest baseRequest = new BaseRequest();
//                baseRequest.setUrl(url);
//                baseRequest.setCacheData(true);
//                baseRequest.setResponseObj(ListUser.class);
//                baseRequest.setCallback(listener);
//                VolleyConnectUtils.getInstance().doPost(baseRequest);
//            }
//
//
//            @Override
//            public void networkSucceed(ListResponse<User> list) {
//                if (list != null && list instanceof ListUser) {
//                    ListUser listUser = (ListUser) list;
//                    L.d(listUser.toString());
//                }
//            }
//        });
//        VolleyConnectUtils.getInstance().doPost()
//    }
}
