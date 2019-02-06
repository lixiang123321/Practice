package com.example.rere.practice.launchmode.base;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rere.practice.base.activity.TestBaseActivity;
import com.example.rere.practice.launchmode.AllowTaskReparentingAct;
import com.example.rere.practice.launchmode.SingleInstanceAct;
import com.example.rere.practice.launchmode.SingleTaskAct;
import com.example.rere.practice.launchmode.SingleTaskAct2;
import com.example.rere.practice.launchmode.SingleTopAct;
import com.example.rere.practice.launchmode.StandardAct;

/**
 * Created by rere on 18-7-10.
 */

public class TestLaunchModeBaseActivity extends TestBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestLaunchModeBaseActivity.class));
    }

    @Override
    protected void addViews(LinearLayout layout) {
        getTextview(layout, "Act : " + this.getClass().getSimpleName());

        getButton(layout, "Standard Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // standard act
                StandardAct.start(mContext);
            }
        });

        getButton(layout, "SingleTop Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SingleTopAct.start(mContext);
            }
        });

        getButton(layout, "SingleTask Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SingleTaskAct.start(mContext);
            }
        });

        getButton(layout, "SingleInstance Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SingleInstanceAct.start(mContext);
            }
        });

        getButton(layout, "SingleTask2 Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                SingleTaskAct2.start(mContext);
            }
        });

        getButton(layout, "AllowTaskReparenting Act", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AllowTaskReparentingAct.start(mContext);
            }
        });
    }
}
