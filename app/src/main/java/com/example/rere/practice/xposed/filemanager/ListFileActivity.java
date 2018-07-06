package com.example.rere.practice.xposed.filemanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.BaseActivity;
import com.example.rere.practice.base.utils.TagLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rere on 18-7-6.
 */

public class ListFileActivity extends BaseActivity {

    private static final String KEY_PATH = "mPath";

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
        String filename = (String) mListViewAdapter.getItem(position);
        if (mPath.endsWith(File.separator)) {
            filename = mPath + filename;
        } else {
            filename = mPath + File.separator + filename;
        }
        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, ListFileActivity.class);
            intent.putExtra("mPath", filename);
            startActivity(intent);
        } else {
            Toast.makeText(this, filename + " is not a directory", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        ((TextView) findViewById(R.id.tv_title)).setText(title);
    }

    
}
