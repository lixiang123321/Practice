package com.example.rere.practice.xposed.test;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by rere on 2018/6/16.
 */

public class TestInVirtualXposedActivity2 extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestInVirtualXposedActivity2.class));
    }

    public static final String KEY_FILE_NAME = "fileTestInVirtualXposed";
    public static final String KEY_PACKAGE_NAME = "com.example.rere.practice";
    public static final String KEY_EXPOSED_VIRTUAL = "io.va.exposed/virtual";
    public static final String KEY_PREFIX_DATA_DATA = "/data/data/";
    public static final String KEY_PREFIX_DATA_USER_0 = "/data/user/0/";
    public static final String KEY_PATH_SEPARATOR = "/";
    public static final String KEY_FILES = "files";


    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "save file in data/data", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save file in data/data
                try {
                    saveDataInOutputStream(mContext.openFileOutput(KEY_FILE_NAME, MODE_PRIVATE));
                } catch (FileNotFoundException e) {
                    TagLog.e(TAG, "onClick() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "read file in data/data", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read file in data/data
                try {
                    readDataInInputStream(mContext.openFileInput(KEY_FILE_NAME));
                } catch (FileNotFoundException e) {
                    TagLog.e(TAG, "onClick() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "get two name", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get two name
                String dataDataFileName = getDataDataFileName(KEY_PACKAGE_NAME, KEY_FILE_NAME);
                String virtualXposeddataDataFileName = getVirtualXposedDataDataFileName(KEY_PACKAGE_NAME, KEY_FILE_NAME);
                TagLog.i(TAG, "onClick() : " + " dataDataFileName = " + dataDataFileName + ",");
                TagLog.i(TAG, "onClick() : " + " virtualXposeddataDataFileName = " + virtualXposeddataDataFileName + ",");
            }
        });

        getButton(layout, "read file in data/data parsed", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read file in other ways
                String dataDataFile = getDataDataFileName(KEY_PACKAGE_NAME, KEY_FILE_NAME);
                try {
                    readDataInInputStream(new FileInputStream(new File(dataDataFile)));
                } catch (FileNotFoundException e) {
                    TagLog.e(TAG, "onClick() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "read file in virtual xposed data/data parsed", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read file in other ways
                String dataDataFile = getVirtualXposedDataDataFileName(KEY_PACKAGE_NAME, KEY_FILE_NAME);
                try {
                    readDataInInputStream(new FileInputStream(new File(dataDataFile)));
                } catch (FileNotFoundException e) {
                    TagLog.e(TAG, "onClick() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "call method for xposed hook", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call Integer for xposed hook
                View view = new View(mContext);
                view.getId();
            }
        });
    }

    private void saveDataInOutputStream(OutputStream fileOutputStream) {
        TagLog.i(TAG, "saveDataInOutputStream() : ");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write("abc");
            bufferedWriter.close();
            fileOutputStream.close();
            TagLog.i(TAG, "saveDataInOutputStream() : finish()");
        } catch (Exception e) {
            TagLog.e(TAG, "saveDataInOutputStream() : " + e.getMessage());
        }
    }

    private void readDataInInputStream(InputStream fileInputStream) {
        TagLog.i(TAG, "readDataInInputStream() : ");
        try {
            //TagLog.i(TAG, "readDataInInputStream() : " + " from = " + mContext.getFilesDir().getAbsolutePath() + ",");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            TagLog.i(TAG, "readDataInInputStream() : " + buffer.toString());
        } catch (Exception e) {
            TagLog.e(TAG, "readDataInInputStream() : " + e.getMessage());
        }
    }

    // TODO: 2018/6/16 handleDifferentTypeFilePath
    private void handleDifferentTypeFilePath(String oriPath) {
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " oriPath = " + oriPath + ",");

        if (TextUtils.isEmpty(oriPath)) {
            return;
        }


        String prefix;
        if (oriPath.startsWith(KEY_PREFIX_DATA_DATA)) {
            prefix = KEY_PREFIX_DATA_DATA;
        } else {
            prefix = KEY_PREFIX_DATA_USER_0;
        }

        String result = prefix + KEY_EXPOSED_VIRTUAL + prefix + KEY_PACKAGE_NAME;
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " result = " + result + ",");
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " oriPath = " + oriPath + ",");
    }

    private String getDataDataFileName(String packageName, String fileName) {
        TagLog.i(TAG, "getDataDataFileName() : " + " packageName = " + packageName + ","
                + " fileName = " + fileName + ",");

        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(fileName)) {
            return "";
        }

        String prefix;
        if (mContext.getFilesDir().getAbsolutePath().startsWith(KEY_PREFIX_DATA_DATA)) {
            prefix = KEY_PREFIX_DATA_DATA;
        } else {
            prefix = KEY_PREFIX_DATA_USER_0;
        }

        String result = prefix + KEY_PACKAGE_NAME + KEY_PATH_SEPARATOR + KEY_FILES + KEY_PATH_SEPARATOR + KEY_FILE_NAME;

        TagLog.i(TAG, "getDataDataFileName() : " + " result = " + result + ",");
        return result;
    }
    
    private String getVirtualXposedDataDataFileName(String packageName, String fileName) {
        TagLog.i(TAG, "getVirtualXposedDataDataFileName() : " + " packageName = " + packageName + ","
                + " fileName = " + fileName + ",");

        if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(fileName)) {
            return "";
        }

        String prefix;
        if (mContext.getFilesDir().getAbsolutePath().startsWith(KEY_PREFIX_DATA_DATA)) {
            prefix = KEY_PREFIX_DATA_DATA;
        } else {
            prefix = KEY_PREFIX_DATA_USER_0;
        }

        String result = prefix + KEY_EXPOSED_VIRTUAL + prefix + KEY_PACKAGE_NAME + KEY_PATH_SEPARATOR + KEY_FILES + KEY_PATH_SEPARATOR + KEY_FILE_NAME;

        TagLog.i(TAG, "getVirtualXposedDataDataFileName() : " + " result = " + result + ",");
        return result;
    }
}
