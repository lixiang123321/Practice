package com.example.rere.practice.xposed.filemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rere.practice.R;
import com.example.rere.practice.base.activity.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rere on 18-7-6.
 */

public class ListFileActivity extends BaseActivity {

    private String path;

    private ListView mListView;
    private ArrayAdapter mListViewAdapter;


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
        path = "/";
        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        // Read all files sorted into the values-array
        List fileNameStrList = new ArrayList();
        File pathDir = new File(path);
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
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }
        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, ListFileActivity.class);
            intent.putExtra("path", filename);
            startActivity(intent);
        } else {
            Toast.makeText(this, filename + " is not a directory", Toast.LENGTH_LONG).show();
        }
    }
}
