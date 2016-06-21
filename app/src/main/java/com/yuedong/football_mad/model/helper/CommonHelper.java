package com.yuedong.football_mad.model.helper;

import android.graphics.Color;

import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目一些帮助方法
 *
 * @author 俊鹏 on 2016/6/8
 */
public class CommonHelper {

    /**
     * 获取用户等级显示信息
     *
     * @param userLevel
     * @return
     */
    public static DisplayUserLevelBean getUserLevelDisplayInfo(int userLevel) {
        DisplayUserLevelBean bean = new DisplayUserLevelBean() ;
        switch (userLevel) {

            case 1:
                bean.textDesc = "资深评论员";
                bean.headBg = R.drawable.level_quan_senior;
                bean.partTextColor = Color.parseColor("#4B8D7F");
                break;

            case 2:
                bean.textDesc = "专业评论员";
                bean.headBg = R.drawable.level_quan_expert;
                bean.partTextColor = Color.parseColor("#D6BC52");
                break;
            case 0:
            default:
                bean.textDesc = "普通评论员";
                bean.headBg = R.drawable.level_quan_common;
                bean.partTextColor = Color.parseColor("#99CC33");
                break;

        }
        return bean;
    }

    /**
     * 获取性别
     * @param sex
     * @return
     */
    public static String getTextBySex(int sex){
        String text = null;
        switch(sex){
            case 1:text="男";break;
            case 2:text="女";break;
            default:text="保密";break;
        }
        return text;
    }

    /**
     * 获取上场位置
     * @param ballpos
     * @return
     */
    public static String getTextByBallPos(int ballpos){
        String text = null;
        switch(ballpos){
            case 1:text="千锋";break;
            case 2:text="中场";break;
            case 3:text="后卫";break;
            case 4:text="门将";break;
        }
        return text;
    }

    /**
     * 获取用户信息根据sid
     * @param sid
     * @param callback
     * @return
     */
    public static String getUserInfo(String sid,VolleyNetWorkCallback callback){
        Map<String, String> post = new HashMap<String, String>();
        post.put("sid", sid);
        return RequestHelper.post(Constant.URL_GET_USER_BY_SID, post, GetUserInfoByIdResBean.class, false, callback);
    }
}
