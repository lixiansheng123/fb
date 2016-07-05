package com.yuedong.football_mad.model.helper;


import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DbAttention;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.db.sqlite.WhereBuilder;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.T;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtils {
    private static DbUtils db;

    static {
        db = DbUtils.create(MyApplication.getInstance().getAppContext());
    }

    /**
     * 获取24小时
     *
     * @return
     */
    public static List<String> getHours() {
        List<String> data = new ArrayList<String>();
        for (int i = 1; i <= 24; i++) {
            data.add(i + "");
        }
        return data;
    }

    /**
     * 获取分钟集
     *
     * @return
     */
    public static List<String> getMinutes() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            String minute = "";
            if (i < 10) {
                minute = "0" + i;
            } else
                minute = i + "";
            data.add(minute);
        }
        return data;
    }


    /**
     * 获取指定年份到当前年份的一个集合
     */
    public static List<String> getYears(int minYear) {
        List<String> years = new ArrayList<String>();
        for (int i = 1940; i <= minYear; i++) {
            years.add("" + i);
        }
        return years;
    }

    /**
     * 获取月份
     */
    public static List<String> getMonth() {
        List<String> month = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                month.add("0" + i);
            } else {
                month.add("" + i);
            }
        }
        return month;
    }

    /**
     * 获取日子 根据年份和月份
     */
    public static List<String> getDays(int year, int month) {
        List<String> days = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
            if (i < 10) {
                days.add("0" + i);
            } else {
                days.add("" + i);
            }
        }
        return days;
    }

    /**
     * 获取我关注的比赛
     */
    public static List<DbAttention> getMyAttentionGameList() {
        try {
            List<DbAttention> datas = db.findAll(Selector.from(DbAttention.class).where("type", "=", 1));
            return datas;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关注比赛
     *
     * @return
     */
    public static boolean attentionGame(String id) {
        DbAttention dbAttention = new DbAttention();
        dbAttention.setId(id);
        dbAttention.setType(1);
        dbAttention.setAttentionTime(System.currentTimeMillis());
        try {
            db.save(dbAttention);
            return true;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 取消关注比赛
     *
     * @param id
     * @return
     */
    public static boolean cancelAttentionGame(String id) {
        try {
            db.delete(DbAttention.class, WhereBuilder.b("id", "=", id).and("type", "=", 1));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 数据库内页关注和取消关注
     */
    public static String attentionData(String userId, int type, String dataid, VolleyNetWorkCallback callback, boolean attention) {
        Map<String, String> post = new HashMap<>();
        post.put("userid", userId);
        post.put("datatype", type + "");
        post.put("dataid", dataid);
        String url = Constant.URL_ATTENTION_DATA;
        if (!attention) url = Constant.URL_UNATTENTION_DATA;
        return RequestHelper.post(url, post, BaseResponse.class, false, false, callback);
    }

    /**
     * 关注球星
     */
    public static void attentionBallStar(final Context con, String sid, String authorid, final VolleyNetWorkCallback listener, final boolean isAttention) {
        Map<String, String> post = getSidPostMap(sid);
        post.put("starid", authorid);
        String url = Constant.URL_INTEREST_STAR;
        if (!isAttention) url = Constant.URL_UNINTEREST_STAR;
        RequestHelper.post(url, post, BaseResponse.class, false, false, new VolleyNetWorkCallback() {
            @Override
            public void onNetworkStart(String tag) {

            }

            @Override
            public void onNetworkSucceed(String tag, BaseResponse data) {
                if (data != null && data.getState().getCode() == Constant.OK) {
                    String msg = "关注成功";
                    if(!isAttention) msg = "取消关注成功";
                    T.showShort(con,msg);
                    listener.onNetworkSucceed(tag, data);

                }
            }

            @Override
            public void onNetworkError(String tag, VolleyError error) {
                if (error != null)
                    T.showShort(con, error.getMessage());
            }
        });
        post = null;
    }


    public static Map<String, String> getUserIdPostMap(String userId) {
        Map<String, String> post = new HashMap<>();
        post.put("userid", userId);
        return post;
    }

    public static Map<String, String> getSidPostMap(String sid) {
        Map<String, String> post = new HashMap<>();
        post.put("sid", sid);
        return post;
    }

    public static Map<String, String> getListPostMap(String page, String count, String max) {
        Map<String, String> post = new HashMap<>();
        post.put("pageindex", page);
        post.put("count", count);
        if (!TextUtils.isEmpty(max))
            post.put("max", max);
        return post;
    }

    public static Map<String, String> getListPostMapHasUserId(String page, String count, String max, String userId) {
        Map<String, String> post = new HashMap<>();
        post.put("pageindex", page);
        post.put("count", count);
        post.put("userid", userId);
        if (!TextUtils.isEmpty(max))
            post.put("max", max);
        return post;
    }


    public static Map<String, String> getListPostMapHasSId(String page, String count, String max, String sid) {
        Map<String, String> post = new HashMap<>();
        post.put("pageindex", page);
        post.put("count", count);
        post.put("sid", sid);
        if (!TextUtils.isEmpty(max))
            post.put("max", max);
        return post;
    }

}
