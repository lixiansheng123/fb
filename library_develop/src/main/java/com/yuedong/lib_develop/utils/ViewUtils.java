package com.yuedong.lib_develop.utils;

import android.view.View;

/**
 * @author 俊鹏 on 2016/6/15
 */
public class ViewUtils {

    public static void hideLayout(View view) {
        hideLayouts(view);
    }

    public static void hideLayouts(View... views) {
        for (View v : views) {
            if (v != null && v.getVisibility() != View.GONE) {
                v.setVisibility(View.GONE);
            }
        }
    }

    public static void invisibleLayout(View view) {
        invisibleLayouts(view);
    }

    public static void invisibleLayouts(View... views) {
        for (View v : views) {
            if (v != null && v.getVisibility() != View.INVISIBLE) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void showLayout(View view) {
        showLayouts(view);
    }

    public static void showLayouts(View... views) {
        for (View v : views) {
            if (v != null && v.getVisibility() != View.VISIBLE) {
                v.setVisibility(View.VISIBLE);
            }
        }
    }
}
