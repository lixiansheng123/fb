package com.yuedong.lib_develop.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


import com.yuedong.lib_develop.utils.L;

import java.util.List;

public class WindowUtils {
    /**
     * 获取手机的宽高
     *
     * @param context
     */

    public static Integer[] getPhoneWH(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int width2 = dm.widthPixels;
        int height2 = dm.heightPixels;
        // PhoneWH wh = new PhoneWH(width2, height2);
        Integer[] it = new Integer[2];
        it[0] = width2;
        it[1] = height2;
        return it;
    }

    ;

    /**
     * 判断是否为平板 好像有点问题最好别用咯
     *
     * @return
     */
    public static boolean isPad(Context con) {
        WindowManager wm = (WindowManager) con
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        // 屏幕宽度
        float screenWidth = display.getWidth();
        // 屏幕高度
        float screenHeight = display.getHeight();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        // 屏幕尺寸
        double screenInches = Math.sqrt(x + y);
        // 大于6尺寸则为Pad
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是平板
     */
    public static boolean isPad2(Context context) {
        return (context.getResources().getConfiguration().screenLayout & //
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void hideSystemBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//    /**
//     * 給状态设置背景 对于5.0以上的手机效果较好· 4.4以上有渐变  4.4以下不支持
//     *
//     * @param activity
//     * @param statusBarBg
//     */
//    public static void setStatusBarColor(Activity activity, int statusBarBg) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(activity, true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(statusBarBg);
//        systemBarTransparent(activity);
//    }
//
//    private static void setTranslucentStatus(Activity activity, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//
//        win.setAttributes(winParams);
//    }
//
//    /**
//     * 让状态栏透明
//     */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private static void systemBarTransparent(Activity activity) {
//        Window window = activity.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);
////            window.setNavigationBarColor(Color.TRANSPARENT);
//
//    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || android.text.TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            L.d("isForeground-ComponentName" + cpn.getClassName());
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
