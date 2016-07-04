package com.yuedong.football_mad.model.helper;


import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DbAttention;
import com.yuedong.lib_develop.db.sqlite.Selector;
import com.yuedong.lib_develop.db.sqlite.WhereBuilder;
import com.yuedong.lib_develop.exception.DbException;
import com.yuedong.lib_develop.utils.DbUtils;
import com.yuedong.lib_develop.utils.T;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
     * @return
     */
    public static boolean attentionGame(String id){
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
        return  false;
    }

    /**
     * 取消关注比赛
     * @param id
     * @return
     */
    public static boolean cancelAttentionGame(String id){
        try {
            db.delete(DbAttention.class,WhereBuilder.b("id","=",id).and("type","=",1));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return true;
    }

}
