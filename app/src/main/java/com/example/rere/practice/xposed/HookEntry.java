package com.example.rere.practice.xposed;

import android.view.View;

import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.xposed.xposedwifi.data.FileUtils;

import java.io.File;
import java.io.IOException;

import de.robv.android.xposed.SELinuxHelper;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.services.BaseService;

/**
 * Created by rere on 18-7-2.
 */

public class HookEntry {

    private static final String TAG = HookEntry.class.getSimpleName();

    public static void hookAtLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        TagLog.x(TAG, "getAndHookDeviceInfo() : " + " lpparam = " + lpparam + ",");
        hookViewGetIdForTest(lpparam);
    }

    private static void hookViewGetIdForTest(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        TagLog.x(TAG, "hookViewGetIdForTest() : ");

        XposedHelpers.findAndHookMethod(View.class.getName(), lpparam.classLoader, "getId", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

                try {
                    hookEntryMethod();
                } catch (Throwable throwable) {
                    TagLog.x(TAG, "beforeHookedMethod() : " + throwable.getMessage());
                }

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });

    }

    private static void hookEntryMethod() {
        try {
            hookBaseServiceDataFileTest();
        } catch (Throwable throwable) {
            TagLog.x(TAG, "hookEntryMethod() : " + throwable.getLocalizedMessage());
        }

        try {
            hookFileTest();
        } catch (Throwable throwable) {
            TagLog.x(TAG, "hookEntryMethod() : " + throwable.getLocalizedMessage());
        }

    }

    private static void hookBaseServiceDataFileTest() throws Throwable {
        TagLog.x(TAG, "hookBaseServiceDataFileTest() : ");

        BaseService bs = SELinuxHelper.getAppDataFileService();
        TagLog.x(TAG, "hookBaseServiceDataFileTest() : " + " bs.hasDirectFileAccess() = " + bs.hasDirectFileAccess() + ",");

        String fileName = "/data/user/0/io.va.exposed/virtual/data/user/0/com.example.rere.practice/files/xposedWifi.json";// good
        String fileStr;
        do {

            try {
                fileStr = FileUtils.readInputStream(bs.getFileInputStream(fileName));
                TagLog.x(TAG, "hookBaseServiceDataFileTest() : " + " fileName = " + fileName + ",");
                TagLog.x(TAG, "hookBaseServiceDataFileTest() : " + " fileStr = " + fileStr + ",");
            } catch (IOException e) {
                TagLog.x(TAG, "hookBaseServiceDataFileTest() : " + e.getMessage());
            }

            fileName = fileName.substring(0, fileName.lastIndexOf("/"));
        } while (fileName.contains("/"));
    }

    private static void hookFileTest() throws Throwable {
        TagLog.x(TAG, "hookFileTest() : ");

        File file;
        boolean isFileExist;
        boolean isFileCanRead;

        String fileName = "/data/user/0/io.va.exposed/virtual/data/user/0/com.example.rere.practice/files/xposedWifi.json";// good
        do {
            file = new File(fileName);
            isFileExist = file.exists();
            isFileCanRead = file.canRead();

            TagLog.x(TAG, "hookFileTest() : " + " fileName = " + fileName + ",");
            TagLog.x(TAG, "hookFileTest() : " + " isFileExist = " + isFileExist + ",");
            TagLog.x(TAG, "hookFileTest() : " + " isFileCanRead = " + isFileCanRead + ",");

            fileName = fileName.substring(0, fileName.lastIndexOf("/"));
        } while (fileName.contains("/"));
    }

}
