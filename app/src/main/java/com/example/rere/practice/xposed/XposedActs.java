package com.example.rere.practice.xposed;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.xposed.test.TestXposedActs;
import com.example.rere.practice.xposed.xposedwifi.XposedWifiActivity;

/**
 * Created by rere on 18-7-6.
 */

public class XposedActs extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, XposedActs.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getButton(layout, "Xposed wifi activity", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xposed wifi activity
                XposedWifiActivity.start(mContext);
            }
        });

        getButton(layout, "test activitys", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test activitys
                TestXposedActs.start(mContext);
            }
        });


    }
}
