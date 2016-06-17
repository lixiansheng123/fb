
package com.yuedong.lib_develop.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

//跟网络相关的工具类
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }


    public static String getIP(Context context) {
        String ip = "null";
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        int state = manager.getWifiState();
        if (state == WifiManager.WIFI_STATE_ENABLED) {
            WifiInfo info = manager.getConnectionInfo();
            int iip = info.getIpAddress();
            ip = (iip & 0xFF) + "." + (iip >> 8 & 0xFF) + "." + (iip >> 16 & 0xFF) + "." + (iip >> 24 & 0xFF);
        } else {
            //获取非wifi情况下的IP地址
            try {
                for (Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces(); ni.hasMoreElements(); ) {
                    NetworkInterface nIf = ni.nextElement();
                    for (Enumeration<InetAddress> address = nIf.getInetAddresses(); address.hasMoreElements(); ) {
                        InetAddress inetAddress = address.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            ip = inetAddress.getHostAddress().toString();
                        }
                    }

                }

            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }

    /**
     * 获取手机Iv4 Ip
     *
     * @return
     */
//    public static String getPhoneIp() {
//        try {
//
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//
//                    .getNetworkInterfaces(); en.hasMoreElements();) {
//
//                NetworkInterface intf = en.nextElement();
//
//                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
//
//                        .hasMoreElements();) {
//
//                    InetAddress inetAddress = ipAddr.nextElement();
//                    // ipv4地址
//                    if (!inetAddress.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
//                        return inetAddress.getHostAddress();
//
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//
//        }
//
//        return "";
//
//    }

}
