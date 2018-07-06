package com.example.rere.practice.xposed.testinvirtual;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by rere on 2018/6/16.
 */

public class TestInVirtualXposedActivity extends TestBaseActivity {

    private static final String KEY_FILE_NAME = "fileTestInVirtualXposed";
    private static final String KEY_PACKAGE_NAME = "com.example.rere.practice";
    private static final String KEY_EXPOSED_VIRTUAL = "io.va.exposed/virtual";
    

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestInVirtualXposedActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "save file in data/data", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save file in data/data
                try {
                    saveDataInDataData(mContext.openFileOutput(KEY_FILE_NAME, MODE_PRIVATE));
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
                    readDataInDataData(mContext.openFileInput(KEY_FILE_NAME));
                } catch (FileNotFoundException e) {
                    TagLog.e(TAG, "onClick() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "get environmentDataPath", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get environmentDataPath
                String environmentDataPath = Environment.getDataDirectory().getAbsolutePath();
                TagLog.i(TAG, "onClick() : " + " environmentDataPath = " + environmentDataPath + ",");
            }
        });

        getButton(layout, "handle different type file path", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle different type file path
                final String path1 = "/data/user/0/com.example.rere.practice/files";
                final String path2 = "/data/user/0/io.va.exposed/virtual/data/user/0/com.example.rere.practice/files/fileTestInVirtualXposed";
                handleDifferentTypeFilePath(path1);
                handleDifferentTypeFilePath(path2);
            }
        });

        getButton(layout, "save file in environment data path", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save file in environment data path
                saveFileInEnvironmentDataPath();
            }
        });

        getButton(layout, "read file in environment data path", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read file in environment data path
                readFileInEnvironmentDataPath();
            }
        });
    }

    private void saveDataInDataData(OutputStream fileOutputStream) {
        TagLog.i(TAG, "saveDataInDataData() : ");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write("abc");
            bufferedWriter.close();
            fileOutputStream.close();
            TagLog.i(TAG, "saveDataInDataData() : finish()");
        } catch (Exception e) {
            TagLog.e(TAG, "onClick() : " + e.getMessage());
        }
    }

    private void readDataInDataData(InputStream fileInputStream) {
        TagLog.i(TAG, "readDataInDataData() : ");
        try {
            TagLog.i(TAG, "readDataInDataData() : " + " from = " + mContext.getFilesDir().getAbsolutePath() + ",");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
            }
            TagLog.i(TAG, "readDataInDataData() : " + buffer.toString());
        } catch (Exception e) {
            TagLog.e(TAG, "readDataInDataData() : " + e.getMessage());
        }
    }

    private void handleDifferentTypeFilePath(String oriPath) {
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " oriPath = " + oriPath + ",");

        if (TextUtils.isEmpty(oriPath)) {
            return;
        }

        final String prefixDataData = "/data/data/";
        final String prefixDataUser0 = "/data/user/0/";

        String prefix;
        if (oriPath.startsWith(prefixDataData)) {
            prefix = prefixDataData;
        } else {
            prefix = prefixDataUser0;
        }

        String result = prefix + KEY_EXPOSED_VIRTUAL + prefix + KEY_PACKAGE_NAME;
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " result = " + result + ",");
        TagLog.i(TAG, "handleDifferentTypeFilePath() : " + " oriPath = " + oriPath + ",");
    }

    private static final String KEY_DIRECTORY_NAME = "testDir";
    private static final String KEY_PATH_SEPARATOR = "/";

    private void saveFileInEnvironmentDataPath() {
        TagLog.i(TAG, "saveFileInEnvironmentDataPath() : ");
        try {
            saveDataInDataData(new FileOutputStream(getFile()));
        } catch (FileNotFoundException e) {
            TagLog.e(TAG, "saveFileInEnvironmentDataPath() : " + e.getMessage());
        }
    }

    //KEY_DIRECTORY_NAME + KEY_PATH_SEPARATOR +
    @NonNull
    private File getFile() {
        File file = new File(Environment.getDataDirectory(), KEY_FILE_NAME);
        file.mkdir();
        return file;
    }

    private void readFileInEnvironmentDataPath() {
        TagLog.i(TAG, "readFileInEnvironmentDataPath() : ");
        try {
            readDataInDataData(new FileInputStream(getFile()));
        } catch (FileNotFoundException e) {
            TagLog.e(TAG, "readFileInEnvironmentDataPath() : " + e.getMessage());
        }
    }
}
