package com.example.rere.practice.wifi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rere.practice.base.utils.TagLog;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by rere on 18-5-3.
 */

public class TestWifiUtils {

    private static final String TAG = TestWifiUtils.class.getSimpleName();

    public static void getWifiInfo(Context context, ViewGroup mLayoutWifi) {
        TagLog.i(TAG, "getWifiInfo() : " + " context = " + context + ",");

        WifiManager wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);

        boolean isWifiOpenByUs = false;
        TagLog.i(TAG, "getWifiInfo() : " + " wifiManager.isWifiEnabled() = " + wifiManager.isWifiEnabled() + ",");
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            isWifiOpenByUs = true;
        }

        boolean finalIsWifiOpenByUs = isWifiOpenByUs;
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                TagLog.i(TAG, "wifi scan onReceive() : " + " intent = " + intent + ",");
                List<String> strings = printWifiInfo(context);

                if (null != mLayoutWifi) {
                    // draw in layout
                    mLayoutWifi.removeAllViews();
                    for (String string : strings) {
                        TextView textView = new TextView(context);
                        textView.setText(string);
                        mLayoutWifi.addView(textView,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }

                /*if (finalIsWifiOpenByUs) {
                    wifiManager.setWifiEnabled(false);
                }*/
                context.unregisterReceiver(this);
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifiManager.startScan();
        TagLog.i(TAG, "getWifiInfo() : " + "start Scan");
    }

    public static List<String> printWifiInfo(Context context) {
        TagLog.i(TAG, "printWifiInfo() : ");
        List<String> mWifiStrList = new ArrayList<>();

        WifiManager wifiManager = (WifiManager)
                context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (null == scanResults || 0 == scanResults.size()) {
            TagLog.i(TAG, "getWifiInfo() : " + "wifi info is null");
            return mWifiStrList;
        }

        TagLog.i(TAG, "getWifiInfo() : start.");

        StringBuffer buffer;
        for (int i = 0; i < scanResults.size(); i++) {
            ScanResult scanResult = scanResults.get(i);
            buffer = new StringBuffer();
            buffer.append("wifi " + i + " : \n" + "" +
                    "\tSSID = " + scanResult.SSID + ",\n"
                    + "\tBSSID = " + scanResult.BSSID + ",");
            TagLog.i(TAG, "getWifiInfo() : " + buffer.toString());
            mWifiStrList.add(buffer.toString());
        }

        TagLog.i(TAG, "getWifiInfo() : end.");
        return mWifiStrList;
    }


    private static void test() throws Exception {
        TagLog.i(TAG, "test() : ");
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iF = interfaces.nextElement();
            byte[] addr = iF.getHardwareAddress();
            if (addr == null || addr.length == 0) {
                continue;
            }
            StringBuilder buf = new StringBuilder();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            String mac = buf.toString();
            TagLog.d("mac", "interfaceName="+iF.getName()+", mac="+mac);
        }
    }

    /**
     * 检查是否有权限
     * @param permission 权限名称 例如 {@link android.Manifest.permission#CALL_PHONE}
     * @return 如果权限开启返回true 如果没有返回false
     */
    public static boolean hasAppPermission(Context context, String permission) {
        if (TextUtils.isEmpty(permission))
            return true;
        if(Manifest.permission.REQUEST_INSTALL_PACKAGES.equals(permission)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return context.getPackageManager().canRequestPackageInstalls();
            } else {
                return true;
            }
        }
        if (getTargetSdkVersion(context) < Build.VERSION_CODES.M) {
            return PERMISSION_GRANTED == PermissionChecker.checkSelfPermission(context, permission);
        } else {
            return PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
        }
    }

    /**
     * 获取target版本信息
     * @return
     */
    public static int getTargetSdkVersion(Context context) {
        int version = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            TagLog.e(TAG, "getTargetSdkVersion() : " + e.getMessage());;
        }
        return version;
    }



}
