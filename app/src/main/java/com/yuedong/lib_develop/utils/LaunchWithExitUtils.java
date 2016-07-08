package com.yuedong.lib_develop.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.yuedong.football_mad.R;

/**
 * Created by Administrator on 2015/11/28.
 */
public class LaunchWithExitUtils {


    private LaunchWithExitUtils() {
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        enterAnim(context);
    }

    public static void startActivity(Activity context, Class<? extends Activity> cls) {
        startActivity(context, cls, null);
        enterAnim(context);

    }

    public static void startActivity(Activity context, Class<? extends Activity> cls, Bundle data) {
        Intent intent = new Intent(context, cls);
        if (data != null)
            intent.putExtras(data);
        context.startActivity(intent);
        enterAnim(context);
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
        enterAnim(fragment.getActivity());
    }


    public static void startActivityForResult(Fragment fragment, Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestCode);
        enterAnim(fragment.getActivity());
    }

    public static void startActivityForResult(Activity activity, Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(activity, cls, requestCode, null);
        enterAnim(activity);
    }


    public static void startActivityForResult(Activity activity, Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        enterAnim(activity);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivityForResult(Activity context, Class<? extends Activity> cls, int requestCode, Bundle data) {
        Intent intent = new Intent(context, cls);
        if (data != null)
            context.startActivityForResult(intent, requestCode, data);
        else
            context.startActivityForResult(intent, requestCode);
        enterAnim(context);
    }

    public static void back(FragmentActivity act) {
        if (act.getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            act.finish();
        } else {
            act.getSupportFragmentManager().popBackStack();
        }
        exitAnim(act);
    }

    private static void enterAnim(Activity act){
        act.overridePendingTransition(R.anim.slide_left_in,
                R.anim.slide_left_out);
    }

    private static void exitAnim(Activity act){
        act.overridePendingTransition(R.anim.slide_right_in,
                R.anim.slide_right_out);
    }
}
