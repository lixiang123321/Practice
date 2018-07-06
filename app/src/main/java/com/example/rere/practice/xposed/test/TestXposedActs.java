package com.example.rere.practice.xposed.test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;

/**
 * Created by rere on 18-7-6.
 */

public class TestXposedActs extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestXposedActs.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "test in virtual Xposed Activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed Activity
                TestInVirtualXposedActivity.start(mContext);
            }
        });

        getButton(layout, "test in virtual Xposed Activity2", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed Activity2
                TestInVirtualXposedActivity2.start(mContext);
            }
        });

        getButton(layout, "test in virtual Xposed environment", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test in virtual Xposed environment
                TestInVirtualXposedEnvironment.start(mContext);
            }
        });
    }
}
