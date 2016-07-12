package com.yuedong.football_mad.app;

import com.yuedong.football_mad.model.bean.User;
import com.yuedong.lib_develop.app.App;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.WindowUtils;

public class MyApplication extends App {
    public User user;
    private static MyApplication instance;
    // 好友变化
//    public boolean friendChange = false;
    // 用户信息变更
    public boolean userInfoChange = false;
    // 关注比赛变更
    public boolean attentionGameChange = false;
    private Integer[] phoneWh;

    public Integer[] getPhoneWh() {
        return phoneWh;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void setLoginuser(User user) {
        if (user != null)
            User.setUserToLocal(user);
        this.user = user;
    }

    public User getLoginUser() {
        if (user == null) {
            User.getUserByLocal();
        }
        return user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        phoneWh = WindowUtils.getPhoneWH(this);
        L.d("手机屏幕:"+phoneWh[0]+"x"+phoneWh[1]);
        instance = this;
        L.isDebug = true;
    }
}
