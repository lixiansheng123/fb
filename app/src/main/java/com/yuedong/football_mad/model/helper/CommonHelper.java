package com.yuedong.football_mad.model.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.yuedong.football_mad.R;
import com.yuedong.football_mad.app.Constant;
import com.yuedong.football_mad.app.MyApplication;
import com.yuedong.football_mad.model.bean.DbLookFriendBean;
import com.yuedong.football_mad.model.bean.DisplayUserLevelBean;
import com.yuedong.football_mad.model.bean.GetUserInfoByIdResBean;
import com.yuedong.football_mad.model.bean.User;
import com.yuedong.football_mad.ui.activity.LoginActivity;
import com.yuedong.lib_develop.bean.BaseResponse;
import com.yuedong.lib_develop.net.VolleyNetWorkCallback;
import com.yuedong.lib_develop.utils.L;
import com.yuedong.lib_develop.utils.LaunchWithExitUtils;
import com.yuedong.lib_develop.utils.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目一些帮助方法
 *
 * @author 俊鹏 on 2016/6/8
 */
public class CommonHelper {
    private static AlertDialog.Builder goLoginBuild;
    private static AlertDialog goLoginDialog;

    /**
     * 获取用户等级显示信息
     *
     * @param userLevel
     * @return
     */
    public static DisplayUserLevelBean getUserLevelDisplayInfo(int userLevel) {
        DisplayUserLevelBean bean = new DisplayUserLevelBean();
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
     *
     * @param sex
     * @return
     */
    public static String getTextBySex(int sex) {
        String text = null;
        switch (sex) {
            case 1:
                text = "男";
                break;
            case 2:
                text = "女";
                break;
            default:
                text = "保密";
                break;
        }
        return text;
    }

    /**
     * 获取上场位置
     *
     * @param ballpos
     * @return
     */
    public static String getTextByBallPos(int ballpos) {
        String text = null;
        switch (ballpos) {
            case 1:
                text = "前锋";
                break;
            case 2:
                text = "中场";
                break;
            case 3:
                text = "后卫";
                break;
            case 4:
                text = "门将";
                break;
        }
        return text;
    }

    /**
     * 获取职业类型
     * @param workType
     * @return
     */
    public static String getWorkType(int workType) {
        String text = "";
        switch (workType) {
            case 0:
            default:
                text = "保密";
                break;
            case 1:
                text = "足球运动员";
                break;
            case 2:
                text = "足球记者/评论员";
                break;
            case 3:
                text = "足球教练员";
                break;
            case 4:
                text = "足球相关工作者";
                break;
        }
        return text;

    }

    /**
     * 获取用户信息根据sid
     *
     * @param sid
     * @param callback
     * @return
     */
    public static String getUserInfo(String sid, VolleyNetWorkCallback callback) {
        Map<String, String> post = new HashMap<String, String>();
        post.put("sid", sid);
        return RequestHelper.post(Constant.URL_GET_USER_BY_SID, post, GetUserInfoByIdResBean.class, false, false, callback);
    }

    /**
     * 检查登录
     */
    public static User checkLogin(final Activity context) {
        User loginUser = MyApplication.getInstance().getLoginUser();
        if (loginUser == null) {
            if (goLoginBuild == null) {
                goLoginBuild = new AlertDialog.Builder(context);
                goLoginDialog = goLoginBuild.setMessage("该功能需要登录,现在去登录?")
                        .setTitle("提示")
                        .setPositiveButton(R.string.str_go_login, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goLoginDialog.dismiss();
                                LaunchWithExitUtils.startActivity(context, LoginActivity.class);
                            }
                        })
                        .setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goLoginDialog.dismiss();
                            }
                        }).create();
            }
            if (!goLoginDialog.isShowing())
                goLoginDialog.show();
        }
        return loginUser;
    }

    /**
     * 设置listview根据item设置高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight;
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.getDividerHeight();
        //获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 新闻评论
     */
    public static String newsComment(String sid, String newsId, String content, VolleyNetWorkCallback callback) {
        Map<String, String> post = new HashMap<>();
        post.put("author", sid);
        post.put("news", newsId);
        post.put("parent", "0");
        post.put("content", content);
        return RequestHelper.post(Constant.URL_ADD_COMMENT, post, BaseResponse.class, false, false, callback);
    }

    /**
     * 数据库内页关注按钮控制
     *
     * @param interest interest=1表示已被关注，interest=0表示未被关注
     * @param type     1：国家2：赛事 3：球队 4：球员 5：其他
     * @param dataid   数据项id
     */
    public static void dataDetailAttentionControl(final Context context, final ImageView img, final int interest, final int type, final String dataid) {
        if (img == null) return;
        if (interest == 0) {
            img.setSelected(false);
        } else {
            img.setSelected(true);
        }
        img.setTag(interest);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                User loginUser = CommonHelper.checkLogin((Activity) context);
                if (loginUser == null) return;
                int attention = (int) v.getTag();
                boolean isAttention = true;
                if (attention == 1) {
                    isAttention = false;
                }
                DataUtils.attentionData(loginUser.getSid(), type, dataid, new VolleyNetWorkCallback() {
                    @Override
                    public void onNetworkStart(String tag) {

                    }

                    @Override
                    public void onNetworkSucceed(String tag, BaseResponse data) {
                        if (data == null) return;
                        if (data.getState().getCode() == Constant.OK) {
                            if ((int) (img.getTag()) == 0) {
                                T.showShort(context, "关注成功");
                                img.setTag(1);
                                img.setSelected(true);
                            } else {
                                T.showShort(context, "取消关注成功");
                                img.setTag(0);
                                img.setSelected(false);
                            }
                        } else {
                            // TODO 后面加入登录异常的判断
                        }
                    }

                    @Override
                    public void onNetworkError(String tag, VolleyError error) {
                        if (error != null) {
                            T.showShort(context, error.getMessage());
                        }
                    }
                }, isAttention);
            }
        });
    }

    /**
     * 获取文章类型icon
     * @param type
     * @return
     */
    public static int getNewsTypeIcon(int type){
        int res = R.drawable.ic_label_jiandi;
        switch(type){
            case 3:
                res = R.drawable.ic_label_post;
                break;
            case 5:
                res = R.drawable.ic_label_saiping;
                break;
            case 6:
                res = R.drawable.ic_label_baike;
                break;
        }
        return res;
    }


}
