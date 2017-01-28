package com.example.rere.practice.main;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.java8.TestJava8Activity;

import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * main activity
 *
 * Created by rere on 2017/1/20.
 */
public class MainActivity extends TestBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "java 8 character", v -> {
            // java 8 character
            TestJava8Activity.start(mContext);
        });
    }

}
