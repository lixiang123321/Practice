package com.example.rere.practice.testvirtualxposedenvironment;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.base.utils.ToastUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * TestInVirtualXposedEnvironment
 * <p>
 * Created by rere on 18-5-29.
 */

public class TestInVirtualXposedEnvironment extends TestBaseActivity {

    private final String mFileName = "data.json";

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestInVirtualXposedEnvironment.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "environment get data dir", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // environment get data dir
                File dataDirectory = Environment.getDataDirectory();
                ToastUtils.showShortMessage(mContext,
                        " dataDirectory.getAbsolutePath() = "
                                + (null != dataDirectory ? dataDirectory.getAbsolutePath() : null) + ",");
            }
        });

        getButton(layout, "mContext.getFilesDir", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mContext.getFilesDir
                File filesDir = mContext.getFilesDir();
                String filesDirPath = null != filesDir ? filesDir.getAbsolutePath() : null;
                ToastUtils.showShortMessage(mContext,
                        " mContext.getFilesDir() = "
                                + filesDirPath + ",");
                TagLog.i(TAG, "onClick() : " + " filesDirPath = " + filesDirPath + ",");
            }
        });

        getButton(layout, "read asset", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read asset
                try {
                    readAsset2DataFile(mContext, mFileName);
                } catch (Exception e) {
                    TagLog.e(TAG, "read asset() : " + e.getMessage());
                }
            }
        });

        getButton(layout, "read data file", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read data file
                try {
                    FileInputStream fileInputStream = mContext.openFileInput(mFileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        TagLog.i(TAG, "read data file() : " + line);
                        TagLog.i(TAG, "read data file() : " + "\n");
                    }

                } catch (Exception e) {
                    TagLog.e(TAG, "read data file() : " + e.getMessage());
                }
            }
        });
    }

    private void readAsset2DataFile(Context context, String fileName) throws Exception {
        InputStream open = context.getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(open));

        FileOutputStream fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.write("\n");
        }

        writer.close();
        reader.close();
    }
}
