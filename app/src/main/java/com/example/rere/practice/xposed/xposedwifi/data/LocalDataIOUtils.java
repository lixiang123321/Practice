package com.example.rere.practice.xposed.xposedwifi.data;


import com.google.gson.Gson;

import com.example.rere.practice.base.BaseApplication;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

/**
 * LocalDataIOUtils
 *
 * Created by rere on 18-5-10.
 */

public class LocalDataIOUtils {

    public static final String KEY_FILE_NAME = "xposedWifi.json";

    private static final String TAG = LocalDataIOUtils.class.getSimpleName();


    public static LocalSavaDataBean readLocalDataFromFile() {
        return readLocalDataFromFile(getLocalDataDataFile());
    }

    public static LocalSavaDataBean readLocalDataFromFile(File file) {
        TagLog.i(TAG, "readLocalDataFromFile() : ");

        try {
            String strFromFile = FileUtils.getStrFromFile(file);
            LocalSavaDataBean localSavaDataBean
                    = new Gson().fromJson(strFromFile, LocalSavaDataBean.class);
            return localSavaDataBean;
        } catch (Exception e) {
            TagLog.e(TAG, "readLocalDataFromFile() : " + e.getMessage());
            return null;
        }
    }

    public static void saveLocalDataToFile(LocalSavaDataBean dataBean, File file) {
        TagLog.i(TAG, "saveLocalDataToFile() : " + " dataBean = " + dataBean + ",");

        String str = new Gson().toJson(dataBean, LocalSavaDataBean.class);

        if (TextUtils.isEmpty(str)) {
            TagLog.w(TAG, "saveLocalDataToFile() : " + "data is empty");
            str = "";
        }

        try {
            FileUtils.setStrToFile(str, file);
        } catch (Exception e) {
            TagLog.e(TAG, "saveLocalDataToFile() : " + e.getMessage());
        }
    }

    public static void saveLocalDataToFile(LocalSavaDataBean dataBean) {
        saveLocalDataToFile(dataBean, getLocalDataDataFile());
    }

    private static @Nullable
    File getLocalDataDataFile() {
        TagLog.i(TAG, "getLocalDataDataFile() : ");
        Context context = BaseApplication.getInstance();
        File filesDir = context.getFilesDir();
        TagLog.i(TAG, "getLocalDataDataFile() : " + " context = " + context + ","+ "filesDir: " + filesDir);
        File file = new File(filesDir.getAbsolutePath(), KEY_FILE_NAME);
        TagLog.i(TAG, "getLocalDataDataFile() : " + "file name : " + file.getAbsolutePath());
        return file;
    }

}
