package com.yuedong.football_mad.model.helper;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DbAttention;
import com.yuedong.football_mad.model.bean.DbLookFriendBean;
import com.yuedong.football_mad.model.bean.User;
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
    public static String attentionData(String sid, int type, String dataid, VolleyNetWorkCallback callback, boolean attention) {
        Map<String, String> post = getSidPostMap(sid);
        post.put("datatype", type + "");
        post.put("dataid", dataid);
        String url = Constant.URL_ATTENTION_DATA;
        if (!attention) url = Constant.URL_UNATTENTION_DATA;
        return RequestHelper.post(url, post, BaseResponse.class, false, false, callback);
    }

    /**
     * 关注球星
     */
    public static String attentionBallStar(final Context con, String sid, String authorid, final VolleyNetWorkCallback listener, final boolean isAttention) {
        Map<String, String> post = getSidPostMap(sid);
        post.put("starid", authorid);
        String url = Constant.URL_INTEREST_STAR;
        if (!isAttention) url = Constant.URL_UNINTEREST_STAR;
        return RequestHelper.post(url, post, BaseResponse.class, false, false,listener);
    }


    /**
     * 关注/取消关注 新闻/专题
     *
     * @param type 1：新闻2：专题
     * @param interestid
     * @param callback
     * @param isAttention
     */
    public static String attentionNews(final Activity context, int type, String interestid, VolleyNetWorkCallback callback, boolean isAttention) {
        User user = CommonHelper.checkLogin(context);
        if (user == null) return null;
        Map<String, String> post = getSidPostMap(user.getSid());
        post.put("type",type+"");
        post.put("interestid", interestid);
        String url = Constant.URL_NEWS_INTEREST ;
        if(!isAttention)url = Constant.URL_NEWS_UNINTEREST;
       return RequestHelper.post(url, post, BaseResponse.class, false, false, callback);
    }

    /**
     * 取消收藏评论
     * @param sid
     * @param commonId
     * @param callback
     * @return
     */
    public static String cancelCollectComment(String sid,String commonId,VolleyNetWorkCallback callback){
        Map<String, String> post = DataUtils.getSidPostMap(sid);
        post.put("commentid",commonId);
       return  RequestHelper.post(Constant.URL_CANCEL_COLLECT_COMMENT,post,BaseResponse.class,false,false,callback);
    }

    /**
     * 获取查看好友信息列表
     * @param userId
     * @return
     */
    public static List<DbLookFriendBean> getLookFriendList(String userId){
        try{
            return db.findAll(Selector.from(DbLookFriendBean.class).where("user_id","=",userId));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 好友红点判断是否显示(判断列表的)
     * @param id 好友的id
     * @param userId 用户id
     * @param updateArticleTime 好友最后更新文章时间
     * @param lookFriendLists 我观看好友的信息列表
     * @return
     */
    public static boolean redPointLignt(String id,String userId,long updateArticleTime,List<DbLookFriendBean> lookFriendLists){
        boolean has = false;
        if(lookFriendLists != null && !lookFriendLists.isEmpty()){
            for (DbLookFriendBean bean : lookFriendLists){
                if(bean.getFriendId().equals(id) && bean.getUserId().equals(userId)){
                    has = true;
                    if(bean.getUpdateArticleTime() == updateArticleTime){
                        return false;
                    }else{
                        bean.setUpdateArticleTime(updateArticleTime);
                        try {
                            db.update(bean);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if(!has) insertFriendLookInfo(id,userId,updateArticleTime);
        return  true;
    }

    private static void insertFriendLookInfo(String friendId,String userId,long updateArticleTime){
        try {
            DbLookFriendBean dbLookFriendBean = new DbLookFriendBean();
            dbLookFriendBean.setFriendId(friendId);
            dbLookFriendBean.setUserId(userId);
            dbLookFriendBean.setUpdateArticleTime(updateArticleTime);
            db.save(dbLookFriendBean);
        } catch (DbException e) {
            e.printStackTrace();
        }

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
