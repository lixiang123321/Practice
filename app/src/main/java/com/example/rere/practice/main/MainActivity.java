package com.example.rere.practice.main;

import com.example.rere.practice.animateview.TestAnimateViewActivity;
import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.callapp.TestCallAppActivity;
import com.example.rere.practice.concurrent.TestLocksActivity;
import com.example.rere.practice.concurrent.TestSynchronizationActivity;
import com.example.rere.practice.concurrent.TestThreadAndExecutorActivity;
import com.example.rere.practice.java8.TestJava8Activity;
import com.example.rere.practice.testdistinct.TestDistinctAct;
import com.example.rere.practice.textviewwithimageview.TestTextViewWithImageViewAct;

import android.os.Bundle;
import android.view.View;
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

        getButton(layout, "concurrency practice part 1 : thread and executors", v -> {
            // concurrency practice part 1 : thread and executors
            TestThreadAndExecutorActivity.start(mContext);
        });

        getButton(layout, "concurrency practice part 2 : synchronization ", v -> {
            // concurrency practice part 2 : synchronization
            TestSynchronizationActivity.start(mContext);
        });

        getButton(layout, "concurrency practice part 2 : locks", v -> {
            // concurrency practice part 2 : locks
            TestLocksActivity.start(mContext);
        });


        getButton(layout, "call other app test", v -> {
            // call other app test
            TestCallAppActivity.start(mContext);

        });


        getButton(layout, "test distinctAct", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test distinctAct
                TestDistinctAct.start(mContext);
            }
        });

        getButton(layout, "test App OpenView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test App OpenView
                TestAnimateViewActivity.start(mContext);
            }
        });

        getButton(layout, "test textview end with imageView", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test textview end with imageView
                TestTextViewWithImageViewAct.start(mContext);
            }
        }).performClick();

    }

}
