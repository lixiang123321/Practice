package com.example.rere.practice.xposed.filemanager;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.BaseActivity;
import com.example.rere.practice.base.utils.TagLog;
import com.example.rere.practice.base.utils.ToastUtils;
import com.example.rere.practice.xposed.xposedwifi.data.FileUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rere on 18-7-6.
 */

public class ListFileActivity extends BaseActivity {

    private static final String KEY_PATH = "Path";

    private String mPath;
    private ListView mListView;
    private ArrayAdapter mListViewAdapter;

    public static void start(Context context, String path) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATH, path);
        context.startActivity(new Intent(context, ListFileActivity.class).putExtras(bundle));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);
        findViews();
        handleData();
    }

    private void findViews() {
        mListView = findViewById(R.id.listview);
    }

    private void handleData() {
        // Use the current directory as title
        mPath = "/";
        if (getIntent().hasExtra(KEY_PATH)) {
            this.mPath = getIntent().getStringExtra(KEY_PATH);
        }
        TagLog.i(TAG, "handleData() : " + " mPath = " + mPath + ",");
        setTitle(this.mPath);

        // Read all files sorted into the values-array
        List fileNameStrList = new ArrayList();
        File pathDir = new File(this.mPath);
        if (!pathDir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] pathDirFiles = pathDir.list();
        if (pathDirFiles != null) {
            for (String file : pathDirFiles) {
                if (!file.startsWith(".")) {
                    fileNameStrList.add(file);
                }
            }
        }
        Collections.sort(fileNameStrList);

        // Put the data into the list
        mListViewAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_2, android.R.id.text1, fileNameStrList);
        mListView.setAdapter(mListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });
    }

    protected void onListItemClick(AdapterView<?> l, View v, int position, long id) {
        String fileName = (String) mListViewAdapter.getItem(position);
        if (mPath.endsWith(File.separator)) {
            fileName = mPath + fileName;
        } else {
            fileName = mPath + File.separator + fileName;
        }
        if (new File(fileName).isDirectory()) {
            ListFileActivity.start(mContext, fileName);
        } else {
            Toast.makeText(this, fileName + " is not a directory", Toast.LENGTH_LONG).show();
            tryOpenFile(fileName);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }

    private void tryOpenFile(String fileName) {
        TagLog.i(TAG, "tryOpenFile() : " + " fileName = " + fileName + ",");
        try {
//            openFileByMimeType(fileName);
            String strFromFile = FileUtils.getStrFromFile(new File(fileName));
            TagLog.i(TAG, "tryOpenFile() : " + " strFromFile = " + strFromFile + ",");
            ToastUtils.showShortMessage(mContext, strFromFile);
        } catch (Exception e) {
            TagLog.e(TAG, "tryOpenFile() : " + e.getMessage());
            ToastUtils.showShortMessage(mContext, "open file failed.");
        }
    }

    private void openFileByMimeType(String fileName) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(fileName));
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        myIntent.setDataAndType(uri, mimetype);
        startActivity(myIntent);
    }


}
