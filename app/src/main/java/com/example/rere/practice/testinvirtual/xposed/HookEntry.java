package com.example.rere.practice.testinvirtual.xposed;

import android.view.View;

import com.example.rere.practice.base.utils.TagLog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

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
                    hookTest();
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

    private static void hookTest() {
        TagLog.x(TAG, "hookTest() : ");



    }

}
