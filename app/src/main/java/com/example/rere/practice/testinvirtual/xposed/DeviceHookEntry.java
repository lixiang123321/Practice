package com.example.rere.practice.testinvirtual.xposed;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by rere on 18-7-2.
 */

public class DeviceHookEntry {

    private static final String TAG = DeviceHookEntry.class.getSimpleName();

    public static void getAndHookDeviceInfo(XC_LoadPackage.LoadPackageParam lpparam) {
//        TagLog.x(TAG, "getAndHookDeviceInfo() : " + " lpparam = " + lpparam + ",");

    }
}
